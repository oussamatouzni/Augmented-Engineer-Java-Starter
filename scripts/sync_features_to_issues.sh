#!/usr/bin/env bash
set -euo pipefail

# Sync markdown files under docs/features (or docs-worktree/docs/features) to GitHub issues.
# Usage: ./scripts/sync_features_to_issues.sh

owner_repo=$(git remote get-url github 2>/dev/null || git remote get-url origin 2>/dev/null || true)
owner_repo=$(echo "$owner_repo" | sed -E 's#.*[:/](.+/.+)$#\1#' | sed 's/\.git$//')

if [ -z "$owner_repo" ]; then
  echo "ERROR: cannot determine owner/repo from git remote. Provide via GITHUB_REPO env var or set 'github' remote."
  echo "Example: GITHUB_REPO=owner/repo ./scripts/sync_features_to_issues.sh"
  exit 1
fi

if ! command -v gh >/dev/null 2>&1; then
  echo "ERROR: gh CLI not found. Install GitHub CLI and authenticate (gh auth login)."
  exit 1
fi

if ! command -v jq >/dev/null 2>&1; then
  echo "ERROR: jq not found. Please install jq (brew install jq)."
  exit 1
fi

base="docs/features"
if [ -d docs-worktree/docs/features ]; then base="docs-worktree/docs/features"; fi

created=()
updated=()
errors=()

echo "Syncing feature files from: $base to repo: $owner_repo"

while IFS= read -r -d '' f; do
  # Extract title from first '# ' heading, fallback to filename
  title=$(sed -n '1s/^# //p' "$f" | sed -n '1p' || true)
  if [ -z "$title" ]; then
    title=$(basename "$f" .md)
  fi

  tmp=$(mktemp)
  sed -n '2,$p' "$f" > "$tmp" || true

  # look for exact title match among issues
  num=$(gh issue list --repo "$owner_repo" --search "$title" --state all --json title,number 2>/dev/null | jq -r --arg t "$title" '.[] | select(.title==$t) | .number' 2>/dev/null || true)

  if [ -n "$num" ] && [ "$num" != "null" ]; then
    if gh issue edit "$num" --repo "$owner_repo" --body-file "$tmp" >/dev/null 2>&1; then
      updated+=("$title|$num")
      echo "UPDATED: $title #$num"
    else
      errors+=("$f: failed to edit #$num")
      echo "ERROR: failed to edit $title (#$num)"
    fi
  else
    # gh issue create prints the URL on success; capture stdout
    create_out=$(gh issue create --repo "$owner_repo" --title "$title" --body-file "$tmp" 2>&1) || create_out="$create_out"
    if echo "$create_out" | grep -E '^https?://' >/dev/null 2>&1; then
      url=$(echo "$create_out" | sed -n 's/.*\(https:\/\/[^ ]*\).*/\1/p')
      num=$(echo "$url" | awk -F'/' '{print $NF}')
      created+=("$title|$num|$url")
      echo "CREATED: $title #$num"
    else
      errors+=("$f: failed to create: $create_out")
      echo "ERROR: failed to create $title: $create_out"
    fi
  fi

  rm -f "$tmp"
done < <(find "$base" -name '*.md' -print0)

# Print JSON summary
printf '{"issues_created": ['
first=true
for c in "${created[@]}"; do
  title=$(echo "$c" | cut -d'|' -f1)
  num=$(echo "$c" | cut -d'|' -f2)
  url=$(echo "$c" | cut -d'|' -f3-)
  if [ "$first" = true ]; then first=false; else printf ','; fi
  printf '{"title": "%s", "number": %s, "url": "%s"}' "$(echo "$title" | sed 's/"/\\"/g')" "$num" "$(echo "$url" | sed 's/"/\\"/g')"
done
printf '], "issues_updated": ['
first=true
for u in "${updated[@]}"; do
  title=$(echo "$u" | cut -d'|' -f1)
  num=$(echo "$u" | cut -d'|' -f2)
  if [ "$first" = true ]; then first=false; else printf ','; fi
  printf '{"title": "%s", "number": %s}' "$(echo "$title" | sed 's/"/\\"/g')" "$num"
done
printf '], "errors": ['
first=true
for e in "${errors[@]:-}"; do
  if [ "$first" = true ]; then first=false; else printf ','; fi
  printf '{"file": "%s", "error": "%s"}' "$(echo "$e" | sed 's/"/\\"/g')" "$(echo "$e" | sed 's/"/\\"/g')"
done
printf ']}'

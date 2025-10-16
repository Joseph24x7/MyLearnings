# Git Version Control

## 1. Steps for Initial Sync with the newly created repository?

```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/Joseph24x7/SpringBootLearning.github
git pull origin main
git push origin main
```

## 2. Difference between git merge vs git rebase?

- **Git Merge**
    - Combines multiple sequences of commits
    - Creates a new commit
    - Non-linear history
    - Easier to use
    - Preserves history

- **Git Rebase**
    - Moves or combines a sequence of commits to a new base commit
    - Linear history
    - More complex
    - Rewrites commit history
    - Cleaner project history

## 3. What is git cherrypick?
- Applies specific commit from one branch to another
- Useful for bug fixes or features
- Command: `git cherry-pick <commit-hash>`

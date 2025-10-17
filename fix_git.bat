@echo off
echo === Backing up markdown files ===
mkdir temp_backup 2>nul
copy notes\DistributedSystems\*.md temp_backup\ /Y

echo === Removing problematic directory ===
rmdir /s /q notes\DistributedSystems

echo === Removing any submodule references ===
git rm --cached -rf notes/DistributedSystems
del .gitmodules 2>nul
rmdir /s /q .git\modules\notes\DistributedSystems 2>nul

echo === Restoring files ===
mkdir notes\DistributedSystems
copy temp_backup\*.md notes\DistributedSystems\ /Y
rmdir /s /q temp_backup

echo === Adding files to Git ===
git add -f notes/DistributedSystems/

echo === Status ===
git status

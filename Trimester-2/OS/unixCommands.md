### **Ashvita’s Commands (20)**

1. **`pwd`** - **Print Working Directory**

   - **Description**: Displays the full path of the current directory.
   - **Example**:
     ```bash
     pwd
     ```

2. **`ls`** - **List Files**

   - **Flags**:
     - `-a`: Show hidden files.
     - `-l`: Detailed view with permissions.
     - `-h`: Human-readable file sizes.
   - **Example**:
     ```bash
     ls -alh
     ```

3. **`cd`** - **Change Directory**

   - **Description**: Navigate between directories.
   - **Example**:
     ```bash
     cd /path/to/directory
     cd ..
     ```

4. **`mkdir`** - **Make Directory**

   - **Flags**:
     - `-p`: Create parent directories if they don't exist.
   - **Example**:
     ```bash
     mkdir -p parent/child
     ```

5. **`rmdir`** - **Remove Directory**

   - **Description**: Removes empty directories.
   - **Example**:
     ```bash
     rmdir my_folder
     ```

6. **`rm`** - **Remove Files/Directories**

   - **Flags**:
     - `-r`: Remove directories recursively.
     - `-f`: Force delete without confirmation.
   - **Example**:
     ```bash
     rm -rf folder_name
     ```

7. **`touch`** - **Create Empty File**

   - **Description**: Creates an empty file or updates an existing file’s timestamp.
   - **Example**:
     ```bash
     touch file.txt
     ```

8. **`cat`** - **Concatenate and Display File Content**

   - **Flags**:
     - `-n`: Number lines in the output.
   - **Example**:
     ```bash
     cat -n file.txt
     ```

9. **`nano`** - **Edit Files**

   - **Description**: Opens the Nano text editor to edit files.
   - **Example**:
     ```bash
     nano file.txt
     ```

10. **`cp`** - **Copy Files/Directories**

    - **Flags**:
      - `-r`: Copy directories recursively.
    - **Example**:
      ```bash
      cp -r folder_name /destination
      ```

11. **`mv`** - **Move/Rename Files**

    - **Description**: Moves or renames files or directories.
    - **Example**:
      ```bash
      mv old_name.txt new_name.txt
      ```

12. **`head`** - **View First Few Lines**

    - **Flags**:
      - `-n`: Specify the number of lines.
    - **Example**:
      ```bash
      head -n 5 file.txt
      ```

13. **`tail`** - **View Last Few Lines**

    - **Flags**:
      - `-n`: Specify the number of lines.
    - **Example**:
      ```bash
      tail -n 5 file.txt
      ```

14. **`chmod`** - **Change File Permissions**

    - **Flags**:
      - `u`: User, `g`: Group, `o`: Others.
      - `+`: Add permission, `-`: Remove permission.
    - **Example**:
      ```bash
      chmod u+x script.sh
      ```

15. **`ls -l`** - **View File Permissions**

    - **Description**: Lists files in a detailed format showing permissions.
    - **Example**:
      ```bash
      ls -l
      ```
16. **`df`** - **View Disk Space Usage**

   - **Flags**:
     - `-h`: Human-readable output.
   - **Example**:
     ```bash
     df -h
     ```

17. **`du`** - **Estimate File/Folder Sizes**

    - **Flags**:
      - `-h`: Human-readable output.
    - **Example**:
      ```bash
      du -h
      ```

18. **`wget`** - **Download Files**

    - **Description**: Downloads files from the web.
    - **Example**:
      ```bash
      wget https://example.com/file.txt
      ```

19. **`uptime`** - **System Uptime**

    - **Description**: Displays how long the system has been running.
    - **Example**:
      ```bash
      uptime
      ```

20. **`date`** - **Current Date/Time**
    - **Description**: Displays the current date and time.
    - **Example**:
      ```bash
      date
      ```

---

### **Shrey’s Commands (20)**

1. **`find`** - **Search for Files/Directories**

   - **Flags**:
     - `-name`: Search by name.
     - `-type`: Specify file type (`f` for files, `d` for directories).
   - **Example**:
     ```bash
     find /path -name "*.txt"
     ```

2. **`locate`** - **Locate Files**

   - **Flags**:
     - `-i`: Case-insensitive search.
   - **Example**:
     ```bash
     locate -i file.txt
     ```

3. **`chown`** - **Change File Ownership**

   - **Description**: Changes the owner of a file or directory.
   - **Example**:
     ```bash
     chown user:group file.txt
     ```

4. **`ifconfig`** - **Network Configuration**

   - **Description**: Displays network interface details (requires `net-tools`).
   - **Example**:
     ```bash
     ifconfig
     ```

5. **`ip addr`** - **View IP Addresses**

   - **Description**: Shows IP address information.
   - **Example**:
     ```bash
     ip addr
     ```

6. **`ps`** - **Display Running Processes**

   - **Flags**:
     - `-aux`: Shows detailed process information.
   - **Example**:
     ```bash
     ps -aux
     ```

7. **`top`** - **Real-Time Process Viewer**

   - **Description**: Displays active processes in real-time.
   - **Example**:
     ```bash
     top
     ```

8. **`kill`** - **Terminate a Process**

   - **Description**: Kills a process by its ID.
   - **Example**:
     ```bash
     kill 12345
     ```
9. **`ping`** - **Test Network Connectivity**

    - **Description**: Sends packets to test network connectivity.
    - **Example**:
      ```bash
      ping google.com
      ```

10. **`curl`** - **Fetch Content from URLs**

    - **Flags**:
      - `-O`: Save the output to a file.
      - `-I`: Fetch headers.
    - **Example**:
      ```bash
      curl -O https://example.com/file.txt
      ```



11. **`tar`** - **Archive Files**

    - **Flags**:
      - `-cvf`: Create an archive.
      - `-xvf`: Extract an archive.
    - **Example**:
      ```bash
      tar -cvf archive.tar file.txt
      tar -xvf archive.tar
      ```

12. **`gzip`/`gunzip`** - **Compress/Decompress Files**

    - **Example**:
      ```bash
      gzip file.txt
      gunzip file.txt.gz
      ```

13. **`zip`/`unzip`** - **Compress/Decompress Files**

    - **Example**:
      ```bash
      zip archive.zip file.txt
      unzip archive.zip
      ```

14. **`whoami`** - **Current User**

    - **Description**: Displays the current user.
    - **Example**:
      ```bash
      whoami
      ```

15. **`who`** - **Logged-In Users**

    - **Description**: Lists logged-in users.
    - **Example**:
      ```bash
      who
      ```

16. **`adduser`** - **Add a New User**

    - **Description**: Creates a new user.
    - **Example**:
      ```bash
      sudo adduser username
      ```

17. **`passwd`** - **Change Password**

    - **Description**: Updates the password for a user.
    - **Example**:
      ```bash
      passwd
      ```

18. **`uname`** - **System Information**

    - **Flags**:
      - `-a`: Show all system details.
    - **Example**:
      ```bash
      uname -a
      ```

19. **`free`** - **Memory Usage**

    - **Flags**:
      - `-h`: Human-readable output.
    - **Example**:
      ```bash
      free -h
      ```

20. **`history`** - **Command History**
    - **Description**: Displays previously run commands.
    - **Example**:
      ```bash
      history
      ```

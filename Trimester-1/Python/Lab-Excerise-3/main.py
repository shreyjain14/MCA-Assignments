import smartscan_registration_module as sr
import os

if __name__ == '__main__':
    folder_path = 'C:/Users/Shrey/Desktop/CODING/mca/Trimester-1/Python/Lab-Excerise-3/qrs'
    for filename in os.listdir(folder_path):
        if filename.endswith('.jpg') or filename.endswith('.png'):
            file_path = os.path.join(folder_path, filename)
            sr.scan_qr_code(file_path)

    sr.print_users()
    
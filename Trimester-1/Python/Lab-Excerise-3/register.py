import qrcode
import os

email = input("Enter your email: ")
name = input("Enter your name: ")


data = f"{name},{email}"


qr = qrcode.QRCode(version=1, box_size=10, border=5)  # type: ignore
qr.add_data(data)
qr.make(fit=True)
folder_path = r"C:\Users\Shrey\Desktop\CODING\mca\Trimester-1\Python\Lab-Excerise-3\qrs"
file_count = len(os.listdir(folder_path))
file_name = f"{file_count}.png"
file_path = os.path.join(folder_path, file_name)

image = qr.make_image(fill="black", back_color="white")
image.save(file_path)

print("QR code generated successfully!")
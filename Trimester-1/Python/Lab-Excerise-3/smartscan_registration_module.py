from PIL import Image

db = []

create_user = lambda name, email: {'name': name, 'email': email}
insert_user = lambda user: (db.append(user), True)[1]
get_all_users = lambda: db

def RegisterUserFromSmartScan(data):
    data_f = data.split(',')
    user = create_user(data_f[0], data_f[1])
    insert_user(user)

def scan_qr_code(image_path):
    import pyzbar.pyzbar as pyzbar
    
    image = Image.open(image_path)
    decoded_objects = pyzbar.decode(image)
    
    for obj in decoded_objects:
        data = obj.data.decode("utf-8")

        RegisterUserFromSmartScan(data)

def print_users():
    for user in get_all_users():
        print(user)
        
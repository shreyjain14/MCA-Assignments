import smartscan_registration_module as sr

if __name__ == '__main__':

    hardcoded = False

    if hardcoded:
        sr.RegisterUserFromSmartScan('Shrey,shrey@shreyjain.me')
        sr.RegisterUserFromSmartScan('Anjaney,mitraanjaney@gmail.com')
        sr.RegisterUserFromSmartScan('Dave,davesharma0002@gmail.com')

    else:
        while True:
            data = input('Enter scanned data [q to exit]: ')
            if data == 'q':
                break
            sr.RegisterUserFromSmartScan(data)
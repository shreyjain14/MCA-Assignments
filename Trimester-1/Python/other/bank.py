NEW_NUMBER = 1000

class Account:

    def __init__(self, name, balance, a_type, num):
        self.name = name
        self.balance = balance
        self.account_type = a_type
        self.account_number = num
        self.transactions = []

    def __str__(self) -> str:
        return str(self.account_number)
    
    def view_balance(self):
        print(f'Account balance: {self.balance}')

    def view_account(self):
        print(f'Account number: {self.account_number}')
        print(f'Account holder: {self.name}')
        print(f'Account type: {self.account_type}')
        print(f'Account balance: {self.balance}')
        print(f'Transactions: {self.transactions}')
    
    def withdraw(self, amt):
        if self.balance - amt < 0:
            print('Insufficient funds')
        else:
            self.balance -= amt
            self.transactions.append(-amt)

    def deposit(self, amt):
        self.balance += amt
        self.transactions.append(amt)

    def change_name(self, name):
        self.name = name

    def change_type(self, a_type):
        self.account_type = a_type


while True:
    print('\n\n------------------------\nWelcome to the bank')
    print('1. Create account')
    print('2. View account')
    print('3. Deposit')
    print('4. Withdraw')
    print('5. Change name')
    print('6. Change account type')
    print('7. Exit\n------------------------\n')
    choice = int(input('Enter choice: '))

    if choice == 1:
        name = input('Enter name: ')
        balance = float(input('Enter balance: '))
        a_type = input('Enter account type: ')
        account = Account(name, balance, a_type, NEW_NUMBER)
        NEW_NUMBER += 1
        print(f'Account created with number {account}')
    elif choice == 2:
        account.view_account()
    elif choice == 3:
        amt = float(input('Enter amount to deposit: '))
        account.deposit(amt)
    elif choice == 4:
        amt = float(input('Enter amount to withdraw: '))
        account.withdraw(amt)
    elif choice == 5:
        name = input('Enter new name: ')
        account.change_name(name)
    elif choice == 6:
        a_type = input('Enter new account type: ')
        account.change_type(a_type)
    elif choice == 7:
        break
    else:
        print('Invalid choice')
    print()
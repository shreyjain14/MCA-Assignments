class Account:
    def __init__(self, acc_no, acc_holder, balance=0):
        self.acc_no = acc_no
        self.acc_holder = acc_holder
        self.balance = balance

    def deposit(self, amount):
        self.balance += amount
        return self.balance

    def withdraw(self, amount):
        if amount > self.balance:
            return "Insufficient balance"
        self.balance -= amount
        return self.balance

    def __str__(self):
        return f"Account No: {self.acc_no}, Account Holder: {self.acc_holder}, Balance: {self.balance}"
    

a = Account(101, "Ram")
print(a)
print(a.deposit(1000))
print(a.withdraw(500))
print(a.withdraw(600))

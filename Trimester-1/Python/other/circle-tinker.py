import tkinter
from time import sleep

class CircleApp:
    def __init__(self):
        self.window = tkinter.Tk()
        self.canvas = tkinter.Canvas(self.window, width=200, height=200)
        self.canvas.pack()
        self.circle = self.canvas.create_oval(50, 50, 150, 150, fill='lightblue')
        self.window.mainloop()

    def move_circle(self):
        self.canvas.delete(self.circle) 
        self.circle = self.canvas.create_oval(100, 100, 200, 200, fill='lightblue') 
        self.window.update()

app = CircleApp()
sleep(1)
app.move_circle()

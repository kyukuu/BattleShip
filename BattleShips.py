import random
import tkinter

class BattleShips:

    def __init__(self,tKinterObject):

        self.main=tKinterObject
        self.main.title("Battleships")  
        self.myGrid=[['Empty']*7 for i in range(7)]     # creating this list to see what blocks have been hit
        self.tries=0        # to calculate the number of tries
        self.shipList=[]    # to store the positions of ships

        while len(self.shipList)<7:     # using this list to store the positions of ships
            x=random.randint(0,6)
            y=random.randint(0,6)
            if [x,y] not in self.shipList:
                self.shipList.append([x,y])

        print(self.shipList)

        self.grid=tkinter.Canvas(self.main,height=175,width=175)
        self.grid.pack(padx=12.5,pady=12.5)

        i=0
        while i<7:
            j=0
            while j<7:
                x1,y1=j*25,i*25
                self.grid.create_rectangle(x1,y1,x1+25,y1+25,fill="white")
                j+=1
            i+=1

        self.chance=tkinter.StringVar()     # initializing the text
        self.chance.set("Start (you get 20 tries)")

        tkinter.Label(self.main,textvariable=self.chance).pack()      # the text

        self.guess_entry=tkinter.Entry(self.main)      # the input
        self.guess_entry.pack(pady=10)    

        tkinter.Button(self.main,text="Enter",command=self.play).pack()     # the button

    def countHits(self):      # to see if the user has won the game

        count=0
        for row in self.myGrid:
            for element in row:
                if element=='Hit':
                    count+=1
        return count

    def play(self):

        try:
            if (self.tries<20) and (self.countHits()<7):
                guess=list(map(int,self.guess_entry.get().split(",")))
                x=guess[0]
                y=guess[1]

                if len(guess)!=2:
                    self.chance.set("Please enter 2 coordinates ")
                    return

                if (x>6) or (x<0) or (y>6) or (y<0):
                    self.chance.set("Input out of range")
                    return

                if self.myGrid[x][y]!='Empty':
                    self.chance.set("Invalid input (already tried)")
                    return                    

                if guess not in self.shipList:
                    self.myGrid[x][y]="Miss"
                    self.chance.set("Miss! Try again")
                    x1,y1=x*25,y*25
                    self.grid.create_rectangle(x1,y1,x1+25,y1+25,fill="red")
                    self.tries+=1

                else:
                    self.myGrid[x][y]="Hit"
                    self.chance.set("Hit! Lessgooo")
                    x1,y1=x*25,y*25
                    self.grid.create_rectangle(x1,y1,x1+25,y1+25,fill="green")
                    self.tries+=1

            elif self.tries==20:
                print("Oops! Out of tries")
                self.chance.set("Oops! Out of tries")
                self.main.quit()

            elif self.countHits()==7:
                print("Congrats! You won in",self.tries,"tries")
                outputStr="Congrats! You won in "+str(self.tries)+" tries"
                self.chance.set(outputStr)
                self.main.quit()
        except:
            pass

program=tkinter.Tk()
game=BattleShips(program)
program.mainloop()

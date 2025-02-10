from mesa import Agent, Model
from mesa.time import RandomActivation

class Person(Agent):
    def __init__(self, unique_id, model):
        super().__init__(unique_id, model)

    def step(self):
        print(f"Agent {self.unique_id} is taking a step")

class SimpleModel(Model):
    def __init__(self, N):
        self.num_agents = N
        self.schedule = RandomActivation(self)

        for i in range(self.num_agents):
            agent = Person(i, self)
            self.schedule.add(agent)

    def step(self):
        self.schedule.step()

model = SimpleModel(5)
for _ in range(3):
    model.step()

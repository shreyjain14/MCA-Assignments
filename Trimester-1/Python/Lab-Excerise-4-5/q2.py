from abc import ABC, abstractmethod
from math import sqrt

class AnalysisError(Exception):
    pass


class DataAnalyzer(ABC):
    @abstractmethod
    def analyze(self):
        pass


class TextDataAnalyzer(DataAnalyzer):
    def analyze(self, data):
        try:
            if type(data) is not dict:
                raise AnalysisError

            total_len = 0
           
            for i in data['data']:
                if type(i) is not str:
                    raise TypeError

                total_len += len(i)

            return {
                "status": "success",
                "total-length": total_len,
                "average-length": total_len / len(data['data'])
            }


        except KeyError as e:
            return {
                "status": "error",
                "error-type": "KeyError",
                "error": str(e)
                }
        except TypeError as e:
            return {
                "status": "error",
                "error-type": "TypeError",
                "error": str(e)
                }
        except ValueError as e:
            return {
                "status": "error",
                "error-type": "ValueError",
                "error": str(e)
                }
        except AnalysisError:
            return {
                "status": "error",
                "error-type": "AnalysisError",
                "error": "AnalysisError occurred"
                }        
        


class NumberDataAnalyzer(DataAnalyzer):
    def analyze(self, data):
        try:
            if type(data) is not dict:
                raise AnalysisError
           
            sum_of_numbers = sum(data['data'])
            sqrt_of_sum = sqrt(sum_of_numbers)
            return {
                "status": "success",
                "sum": sum_of_numbers,
                "square-root-of-sum": sqrt_of_sum
            }


        except KeyError as e:
            return {
                "status": "error",
                "error-type": "KeyError",
                "error": str(e)
                }
        except TypeError as e:
            return {
                "status": "error",
                "error-type": "TypeError",
                "error": str(e)
                }
        except ValueError as e:
            return {
                "status": "error",
                "error-type": "ValueError",
                "error": str(e)
                }
        except AnalysisError:
            return {
                "status": "error",
                "error-type": "AnalysisError",
                "error": "AnalysisError occurred"
                }        
        

if __name__ == '__main__':

    instances = [TextDataAnalyzer(), NumberDataAnalyzer()]
    data = [
        {'data': [0,1,2,3,4,5]},
        {'data': [0,1,2,-4]},
        {'data': ['a', 'b', 'c', 'd', 'e']},
        {'data': [1, 2, 3, 4, 5, 'a']},
        {'data': 'abc'},
        12,
        'abc',
        {'a': 1},
        [10, 20, 'abc']
    ]

    for i in data:
        for instance in instances:
            print(f"\n\nData: {i} \nAnalyzer: {instance.__class__.__name__}\nResult:")
            print(instance.analyze(i))
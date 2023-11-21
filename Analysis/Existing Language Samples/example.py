# Fibonacci Program

# Returns list of first n Fibonnacci numbers
# Argument and Return Type prompts are optional
def GetFibSequence(n: int) -> list[int]:
    seq: list[int] = []
    for i in range(0, n):
        if i == 0 or i == 1: seq.append(i)
        else: seq.append( seq[-1] + seq[-2] )
    return seq

print(GetFibSequence(10))
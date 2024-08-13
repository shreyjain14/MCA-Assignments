ordered_part = ['Start', 'River']
jumbled_part = ['Clearing', 'Village', 'Cave']

def reconstruct_path(ordered_part, jumbled_part):
    res = []
    for i in range(len(ordered_part)):
        res.append(ordered_part[i])
        res.append(jumbled_part[i])
    res.append(jumbled_part[-1])
    return res

print(reconstruct_path(ordered_part, jumbled_part))
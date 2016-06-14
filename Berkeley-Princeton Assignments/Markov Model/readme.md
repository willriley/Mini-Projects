## [Here's](http://www.cs.princeton.edu/courses/archive/spr16/cos126/assignments/markov.html) the assignment description

In essence, I used Markov models in order to randomly generate text based on a given input. Each Markov model had an order, and, based on
that order, predicted what the next character would be with fixed probability. For instance, say your input has 100 occurences of "thi". Of those, 60 are "the", 25 "thi", 10 "tha", and 5 "tho".
A Markov model of order 2 would predict that the next letter would be "e" with probability 3/5, "i" with probability 1/4, "a" with probability 1/10, and "o" with probability 1/20. 

This simple premise formed the crux of the model: a symbol table filled with keys like "th" that connected to an int array. 
Each character following the key was typecasted into an int, and that index in the array was incremented. 
Then, using that int array as a set of probabilities, I could generate a new character chosen with weight proportional to how often 
it followed the specified key like "th" in the text.

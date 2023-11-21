/*
FizzBuzz Program
Iterate over each number
1 to n. 
*   If n is a multiple of
    3, print Fizz
*   If n is a muliple of
    5, print Buzz
*   If both, print FizzBuzz
*   Otherwise, print the number
*/

function FizzBuzz (n) 
{
    for (let i = 1; i <= n; i++)
    {
        let output = "";
        
        if (i % 3 == 0)
        {
            output += "Fizz"
        }
        
        if (i % 5 == 0)
        {
            output += "Buzz"

        }

        if (output) {
            console.log(output)
        } else {
            console.log(i)
        }

    }   
}

// Call FizzBuzz from 1 to 100
FizzBuzz(100)
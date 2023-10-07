The statement count `+= 1;` is identical in meaning to `count = count + 1`. The add and assign operator (**+=**) adds and assigns in one operation. You can also use:
	the subtract and assign operator (**-=**)
	the multiply and assign operator (**\*=**)
	the divide and assign operator (**/=**)
	the remainder and assign operator (**%=**)

**Prefix vs Postfix**
`b = 4; `
`c = ++b;`
b and c hold 5
`b = 4; `
`c = b++;`
b holds 5, c holds 4

**`for` Loop Examples**
`for(g=0, h=l ; g < 6 ; ++g)`
`for(g=O; g<3 && h>l ; ++g)`
`for(g=O; g<10; ++g, ++h, sum+=g)`
`for(;;)` (This is an infinitely executing statement, like `while(true)`)
You can have multiple (or zero) arguments / expressions in each of the three parts of a for loop.
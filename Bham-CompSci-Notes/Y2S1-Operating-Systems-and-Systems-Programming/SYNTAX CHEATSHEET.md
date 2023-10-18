Array: `int name[15]` is a 15 sized integer array from `name[0]` to `name[14]`
2D Array: `int name[2][5]` is an array with 2 rows and 5 columns
#### Pointers: 
`int *p = &c`, `*p` is an int pointer, towards the memory address of c.
`int b = *p;` means that whatever is at `*p` is copied into `b`.
`p+1` points to the memory address next (i.e. +4 bytes for int)
`char *ptr = "Comp Sci";` means that we are declaring a pointer array, and pointing to the 1st char, but this will be read only.
`int *foo(...)` is a function that returns a pointer to an `int`
`int *p = (int *) malloc(3*sizeof(int));` is allocation of memory for 3 `int` at `*p`
`free(p)` frees the memory allocated at `p`
`void *pointer_name;` is a void pointer, that has no associated datatype and can point to any type


#### Structs:
```c
struct Person{ 
	int Age;
	char *Name;
};
```
This is the declaration of a struct `Person`, with an `int` and `char *`.
Structs are instantiated with `struct Person tom`.
Struct values are accessed with `tom.Name = "Tom"`, this is direct access.
Could instead `tom→age = 25;`, where `tom->age` is a shortcut for `(*tom).age`, this is access using a pointer.


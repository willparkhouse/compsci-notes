# Problem Sheet for Week 1

## Lab Video

We will begin with a short introductory
[video](https://bham.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?id=63337510-f4dc-4c28-a4cf-af1d00af4c6b).

## The Jupyter Notebook

We will be using Jupyter, a web-based interactive development
environment in order to have a standard platform for all the tasks in
this course.  The School of Computer Science maintains its own
instances of the service which you may connect to
[here](https://jupyterhub.oc1.aws.cs.bham.ac.uk/).  Our first problem
set will be concerned with familiarizing yourselves with this
development environment.

## Important: Shutting Down

Time on the server is allocated with a quota.  If you use up all the
time allotted to you, you will no longer be able to access the system.
For this reason, please remember to shut down your server instance
when you are finished with a session.  You may do so by selecting "Log
Out" from the File menu.

## The Terminal

Jupyter includes a Unix terminal system which can be used to create
files and directories, compile source modules and run programs.  If
you are unfamiliar with the Unix terminal, this first exercise will
show you some of the most basic commands.

1. To open the terminal, select "Terminal" from the Jupyter launcher.
You should then see a command prompt with a blinking cursor.

1. The command `pwd` is for "print working directory".  Typing it and
pressing enter will show you your current location in the system.

1. You can create a new directory with the command `mkdir`.  Try,
for example, `mkdir scratch` to create a new scratch directory.

1. You can change directories with the command `cd`.  Try `cd scratch`
to change into the directory you just created.

1. There are many ways to create new files.  A simple one is with the
`touch` command.  Try `touch foo.txt` to create an empty text file.

1. You can list the files in the current directory with the `ls` command. Try it
now to see the file you create. For more information about the files, you can
try `ls -l` which will show you, for example, the creator, the size and the date
and time of creation.

1. To include *hidden* files in the list (which are files beginning
with a ".") you can use the command `ls -la`. Note that there are two
special hidden files (which are actually hidden *directories*) namely
`.` which means the current directory, and `..` which means the parent
directory.  Try changing to these directories to see what happens.

1. `cd ..` goes to the parent directory.

1. `cd ~` or `cd` go to your home directory.

1. `cd -` goes to the directory you where before you did your last `cd` command.


1. `cp foo.txt myfoo.txt` creates a new file `myfoo.txt` with the contents of
   the existing file `foo.txt`. You are encouraged to copy files with `cp` like
   this to create new copies of the provided files and experiment with them by
   making your own modifications.

1. `rm myfoo.txt` removes the file `myfoo.txt` from the current directory.

1. `mv myfoo.txt MyNewLife.hs` renames the file `myfoo.txt` to `MyNewLife.hs`

1. `mv myf<tab>` will complete the name if there is a unique completion. This
   kind of `<tab>` completion can be used for any command, including `cd`. The
   `<tab>` key may have different symbols in different keyboards. For example,
   on a Mac it looks approximately like `->|`.

1. `grep <string> *.md` finds all `md` files that mention the string. For
   example, you can use `grep type *.md` to find the lecture notes that contain
   the word `type` if you are in the lecture notes directory. This is handy for
   finding information in the lecture notes. (`grep` stands for global regular
   expression search and find.)

1. `find . -name myfoo.txt` searches the current directory and its
   subdirectories to try to find your file.

1. `find ~ -name "*foo.txt"` searches your home directory and subdirectories to
   find any file whose name ends with `foo.txt`, including `myfoo.txt`. You can also do e.g. `find .. -name "*foo*.txt"`. The stars
   are wild cards.

1. You can use the up and down arrows in your keyboard to find commands you already typed and run them again.

1. Learn [more
   commands](https://duckduckgo.com/?t=ffab&q=bash+summary+of+commands&atb=v358-1&ia=web)
   to make your life easier.

We will meet more commands in some of the exercises below.

## Cloning the course repository

Git is one of the most popular available *version control systems*.
It is used to keep track of files as they are revised and changed
during the development process.  Your module team uses this system to
update and revise the course materials.


Our first step is to setup access to the course's GitLab repository
from Jupyter.

1. Log on to `Jupyter`

1. Open a terminal

1. Run the command
   ```
   /jupyter/home/.local/bin/setup-git
   ```

1. The script will print out a public key which you must copy by hand to the
   GitLab server. Visit https://git.cs.bham.ac.uk/-/profile/keys and paste the
   output from the script in the box labelled **Key**.

1. You may wish to give your key a name like "Jupyter Key" in the box labelled
   **Title**

1. Click `Add Key`.

1. Back in the Jupyter terminal, ensure you are in the /jupyter/work/ folder, then run the command
   ```
   git clone git@git.cs.bham.ac.uk:fp/fp-learning-2023.git
   ```
   to copy the course repository to your Jupyter instance.

1. When the cloning process has finished, you should see a new folder named
   `fp-learning-2023` in your working folder. Folders are also called
   directories in linux.

1. Explore the resulting folder with the Unix commands you have learned above.

   **Note** If for any reason you need to see the the public ssh key generated
   from the above steps, it is stored in the file `/jupyter/work/.ssh/gitlab.pub`.  You can
   view the contents of this file with the command
   ```
   cat /jupyter/work/.ssh/gitlab.pub
   ```

## Running and Compiling the Game of Life

The source code for the Game of Life program discussed in this week's
lecture can be found in the directory [/files/LectureNotes/Sections](fp-learning-2023/files/LectureNotes/Sections).

1. Let's first create a copy of the file to work with. To copy a file, we use
   the `cp` command. (Note the use of the parent directory `..` in the `cp`
   command).
   ```
   cd /jupyter/work
   mkdir life
   cd life
   cp ../fp-learning-2023/files/LectureNotes/Sections/Life.hs .
   ```
   Note that the `.` at the end of the final command is crucial. It tells the
   `cp` command that the destination folder is the current one.

1. We can run the program directly with the following command
   ```
   runhaskell Life.hs
   ```

1. Stop the program with `Ctrl-c`.

1. Alternatively, we can compile the source to an executable in order
   to be able to run it directly from the system.  This can be done
   with ghc's "make" option which will take care of compiling, linking,
   as well as including any dependencies.
   ```
   ghc --make Life.hs
   ```

1. We can run the resulting executable by entering
   ```
   ./Life
   ```

## Modifying the Game of Life

1. To view the source, open the file with the text editor.

1. By default, the program uses the "glider" grid defined at the top of the file.  We
   can see this on line 71:
   ```
   main :: IO ()
   main = life glider
   ```

1. Try switching to another one of the defined grids.

1. What character is being used to represent live cells on the screen?
   Try changing it to a character of your choice.

1. Try adding a grid of your own. Some fun examples can be found on
   the Wikipedia page for the [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

## Making and Interpreting A Haskell Source module

1. Make a new file named `foo.hs` in a directory of your choice.  You
   may do so with the either the terminal or the file browser.

1. Add the following contents:
   ```hs
   double :: Int -> Int
   double x = x + x
   ```

1. Open a new terminal window.

1. Run `ghci` to start the Haskell interpreter.

1. Load the new file as follows:
   ```
   Prelude> :l foo.hs
   [1 of 1] Compiling Main             ( foo.hs, interpreted )
   Ok, one module loaded.
   ```

1. Run the function `double` with your favorite number:
   ```
   *Main> double 6
   ```

## Inspecting Some Types in Ghci

Haskell is a *strongly typed language* which means that all expressions in the
language are assigned a *type* describing how they may be used. We can use the
Haskell interpreter `ghci` to find out the type various expressions. Inside of
`ghci` enter
```
:t True
```
You should see the output
```
True :: Bool
```
which indicates that the expression `True` is a boolean.

Use the same technique to find the types of the following expressions.

1. `not (not (not False))`

1. `(True,False)` (see Section 3.4 of Programming in Haskell)

1. `['a', 'b', 'x']` (see Section 3.3 of Programming in Haskell)

1. `[(3,4),(4,6)]`

1. `(++)` (What is strange about this type? See "polymorphic functions" discussed later.)

## More with Ghci

If you would like to continue playing with `ghci`, the first chapter of [Learn
You a Haskell for Great
Good!](https://git.cs.bham.ac.uk/fp/fp-learning-2023/-/blob/main/files/Resources/LearnYouaHaskell/LearnYouaHaskell.pdf)
contains many more examples which you can try out in order to start
familiarizing yourself with Haskell syntax.

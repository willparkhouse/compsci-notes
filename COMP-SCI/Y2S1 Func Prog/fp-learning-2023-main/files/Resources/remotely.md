# Working at the School Lab remotely from your own machine

Warning: this requires more advanced computer skills and is not officially supported.

## This is available as is, without support

The School of Computer provides Jupyter and IT support for it.

Here we explain a secret alternative, which may not always work.
Please notice that we don't provide support for this, and neither do IT services, so please don't contact them. You may ask questions on Teams, and, if we are able to help, we will.

The general idea is this:

1. "Mount" your home directory from the School file system so that it appears to be in your own home machine and you can edit your remote `Haskell` files using your own machine and operating system.

1. Connect to the lab to be able to run `Haskell` in the School lab from home.

In order to perform these two steps, you will need to have `sshfs`
(secure shell file system) and `ssh` (secure shell) installed, as
explained below. Notice that, in all cases, `ssh` is installed
automatically (if it is not already in your system) when you install
`sshfs`.

## Important -  selection of user name and password

In the following instructions, you have to use the same username and password as for `Jupyter Notebook`.

## Installing `ssh` and `sshfs`

### Linux

In Ubuntu, open a terminal and run
```
$ sudo apt install sshfs
```

This also works for many other Debian-based distributions of Linux.

If you use a different distribution of Linux, you may need to replace `apt` by the package manager of your distribution. Please check the documentation of your Linux distribution to learn how to install packages. But in any case the package will be called `sshfs`.

### Mac

Assuming that you have the [Homebrew](https://brew.sh) package manager installed
on your Mac, running the following two commands should be sufficient:

```
$ brew cask install osxfuse
$ brew install sshfs
```

If you do not have Homebrew installed, you can install it by running the
following command

```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

This should work but some students running macOS Catalina reported experiencing
some problems. If you encounter any issues, contact us on the [Teams student support chat](https://teams.microsoft.com/_#/school/conversations/General?threadId=19:90a79d3909ab4b83aea0f2539380570f@thread.tacv2&ctx=channel).

### Windows

See this 2-minute [you tube video](https://www.youtube.com/watch?v=uiXOuxdadms).

You will need `\\sshfs\username@tw.cs.bham.ac.uk` to replace a similar string in the video, where `username` is your user name in the School of Computer Science lab, rather than your University user name.

## Mounting locally the remote School file system

In Windows you don't need to do anything if you have performed the above instructions.

For both Linux and Mac, you need to run the following in a terminal. First create a new folder to mount the remote file system locally. Let's call it `socs` for School of Computer Science:
```
$ mkdir socs
```
Then each time you want to work, mount the file system as follows:

```
$ sshfs username@tw.cs.bham.ac.uk: socs
```

where `username` is your user name in the School of Computer Science lab, rather than your University user name.

## Connecting to the lab

Now, in any of Linux, Mac or Windows, open a command-line terminal and run
```
$ ssh username@tw.cs.bham.ac.uk
$ ssh-lab
$ module load ghc
```

Notice that, again, `username` is your user name in the School of Computer Science lab, rather than your University user name.

* The first `ssh` is to connect to the student's gateway machine `tinky-winky`, abbreviated `tw`. You will get a terminal running there.
* From this terminal, the command `ssh-lab` takes you to a random machine in the lab in room UG04 of the Computer Science building. The randomness is to balance the load.
* Now in the lab machine terminal, the command `module load ghc` will make `ghci` available.
* Notice that this is an older version of Haskell.
* So any work you do in the lab, remotely or in person, should be triple checked on Jupyter before submission.

  If you don't do this, you get an even older version, which doesn't work at all for us, as there have been significant changes since then, so that the old version is rather obsolete and useless for us, and will give you many errors that will be difficult to make sense of.

## Workflow

Now you are ready to go.

1. Edit and save the remote program files with your favourite text editor in your own operating system.

2. In the lab terminal you have opened, run the program.

3. Alternate between (1) and (2) by moving the mouse pointer to the local editor and remote terminal windows as appropriate.

4. You will need to use the commands `runhaskell` and `ghci` in the remote terminal, as explained in another [handout](../Assignments/Lab1/README.md).

If you switch off the machine you will need to establish the `sshfs` connection again following the above recipe. Sometimes it may drop while you are working, and so you will have to establish it again. Similarly, if you switch off your machine you will need to connect to a lab machine with `ssh` next time you switch it on.


## Recommended text editors

Editors are a matter of personal preference. The following all have syntax highlighting for Haskell as addons:

1. `gedit` - simple and effective.
1. `sublime` - very popular, widely adopted by developers in all programming languages, and includes advanced features.
1. `emacs` - a powerful editor, with steep learning curve, for seasoned experts.
1. `vim` - a powerful editor, with steep learning curve, for seasoned experts.

You can search the web to find other popular editors for Haskell.

## Failing to connect with `sshfs`?

* Some students reported that this was a problem with their internet providers blocking content. In that case, you should go to the settings page of your internet provider and unclick any blocking setting such as an antivirus.

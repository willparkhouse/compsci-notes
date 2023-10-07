# [Functional Programming 2023/2024](https://canvas.bham.ac.uk/courses/70799)

We will learn *functional programming* using the programming language *Haskell* as a vehicle.

## Important general information

This page is updated periodically with new learning material.

* [Lecture Notes](REPO/COMP-SCI/Y2S1%20Func%20Prog/fp-learning-2023-main/files/LectureNotes/README.md)

* [Weekly Activity Plans](REPO/COMP-SCI/Y2S1%20Func%20Prog/fp-learning-2023-main/files/ActivityPlans/README.md)

* [Problem sheets](files/ProblemSheets/)

* [Teams student support](https://teams.microsoft.com/l/team/19%3aKjPR7DwRM55w0N2viFbdxtdjDShgu1TebFuou_Zmawk1%40thread.tacv2/conversations?groupId=d2fcc432-c365-424c-af12-19ab3674f0ac&tenantId=b024cacf-dede-4241-a15c-3c97d553e9f3). It is important to join this as soon as possible, because this is where you can ask questions, to be answered by module lecturers, teaching assistants, demonstrators, and your colleagues, as well as discuss things about the module contents.

* [Jupyter Notebook](https://jupyterhub.oc1.aws.cs.bham.ac.uk/)

* Lectures:

   * Edgbaston. [Teaching and Learning - TLB-LG18](https://conferences.bham.ac.uk/venues/teaching-learning-building/), Wednesdays 9:00am-11:00am (UK).

   * Dubai. Harvard Lecture Theatre - 0401, Tuesdays 11:00am-1:00pm (UAE).

* Edgbaston labs are in UG04. There are four sessions.

   1. Thursdays 2-3pm UG04
   1. Thursdays 3-4pm UG04.
   1. Fridays 2-3pm UG04.
   1. Fridays 3-4pm UG04.
   1. ~~Fridays 3-4pm LG04.~~ This session in LG04 is cancelled. If you have been allocated to it, please choose any of the above four sessions.

* [Module calendar](#calendar).

* **Important** mandatory reading: [Plagiarism](#plagiarism).

* Installing Haskell in your own machine is not necessary, because we provide a Haskell environment for you, called `Jupyter`, that you can access from a browser, but it is [possible](installing-haskell.md) although we don't provide support for that. You also can use Haskell from the UG04 lab machines.

* Find library functions with [hoogle](https://hoogle.haskell.org/)

* [Canvas page](https://canvas.bham.ac.uk/courses/70799)

* We don't monitor Canvas messages. We also don't monitor email other than for Welfare issues. Please use Teams instead.

## Typos, imprecisions, and bugs

If you find them in the files here (they certainly are or will be present), including this one, you can have a go and submit an [issue](https://git.cs.bham.ac.uk/fp/fp-learning-2023/-/issues) or, more ambitiously, [merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html). If it is relevant we will merge it (and if it isn't, we will politely explain). Merge requests and issues are much better than Canvas messages, Teams discussions and email messages. Any important pull request or issue will be reported on Canvas and Teams, though, after we have discussed it here on gitlab, and feel free do that yourself. Please contribute.

You can see how to contribute and create merge requests [here](CONTRIBUTING.md).

## [Official module textbook](https://rl.talis.com/3/bham/lists/D8A4E97C-76C1-121A-7100-E513B9C6B342.html?lang=en)

* [Programming in Haskell, 2nd edition](http://www.cs.nott.ac.uk/~pszgmh/pih.html) by Graham Hutton

This is available to you from the University Library via Kortex as an eBook:

* [Find it here](https://rl.talis.com/3/bham/lists/D8A4E97C-76C1-121A-7100-E513B9C6B342.html?lang=en)

There are also some hardcopies available at the library for you to borrow.

* [Book Errata](http://www.cs.nott.ac.uk/~pszgmh/pih-errata.html)

* [Book slides](files/Resources/Book/slides)

* [Book code](files/Resources/Book/code)

## Some free books

* [Learn you a Haskell for Great Good](https://learnyouahaskell.github.io/). This is a good, easygoing, enjoyable, informal, and occasionally irreverent, complement to the adopted book.

* [A Gentle Introduction to Haskell](https://www.haskell.org/tutorial/), by Paul Hudak from Yale University, one of the leading designers of the language.

* [Real World Haskell](http://book.realworldhaskell.org/read/). Focuses on practical applications.

## Assessment

* 100% continuous assessment. If you fail the continuous assessment, you can take a supplementary exam, capped to 40%, which gives the final module mark without taking the continuous assessment into account. See the [module syllabus](https://www.cs.bham.ac.uk/internal/modules/2023/06-34253/).

## Coursework format

### [Assignments](https://canvas.bham.ac.uk/courses/70799/assignments)

All of the following are mandatory.

### Formative work

  Formative work is for learning and is not assessed. You get an exercise sheet at the weekly online lab sessions on Thursdays 9-11am on Zoom, where you can get help and feedback from lecturers and teaching assistants. You will solve some exercises during the lab sessions, and the remaining ones in your own time.

  Discussion with colleagues in formative work is encouraged and *not* considered to be plagiarism. The purpose is to learn, rather than to be assessed, and discussing with colleagues is a great way to learn. However, you should still produce your own work and avoid plain copying, as this does not aid learning in any way and will leave you unprepared for the assessed assignments.

### Assessed assignments

  These are marked and the mark counts towards your module mark.
  Collaborating or copying in assessed assignments will be considered to be [plagiarism](https://intranet.birmingham.ac.uk/as/studentservices/conduct/plagiarism/index.aspx).
  In this vein, make sure that you do *not* post solutions to any of the assessed assignments to a public repository. The purpose is to be assessed, rather than just learn.
  It is important to reader further discussion of plagiarism below, as this has serious consequences.

#### Test 1 (40% of module mark)

Online, Thursday 9th November, 12:00-14:00 (+ extra time for RAP students).

This test can be missed with Welfare dispensation.

#### Test 2 (60% of module mark)

In person, Thursday 7th December, 12:00-14:00 (+ extra time for RAP students).

If you miss this test with Welfare dispensation, you have to take an exam in the Supplementary Exam period in August as a first sit, uncapped.

#### Important notice

Notice that the tests are 12:00-14:00 and **not** 14:00-16:00.

The tests can be taken only during the allocated time with no exceptions.

## Marking and feedback

The marking is automatic and produces detailed feedback. We also have feedback lectures, and you can get further feedback in the labs and the [Teams student support](https://teams.microsoft.com/l/team/19%3aKjPR7DwRM55w0N2viFbdxtdjDShgu1TebFuou_Zmawk1%40thread.tacv2/conversations?groupId=d2fcc432-c365-424c-af12-19ab3674f0ac&tenantId=b024cacf-dede-4241-a15c-3c97d553e9f3).

## Learning

There is much more to learning than just attending lectures and doing assignments. We expect you to go beyond the lectures, for example by critically reading the material we give to you, but also by finding out and exploring by yourself, experimenting with your own examples, reading code that we include in GitLab but don't discuss in the lectures, etc. The formative and assessed assignments will also include some amount of guided self-learning.

<a name="plagiarism"></a>
## <a id="Plagiarism"></a>Plagiarism

Plagiarism will not be tolerated: it is unfair to the other students and prevents students from learning, and would give them a mark they don't deserve, and qualifications they don't deserve, hence being unfair to Society as a whole too. Please behave well and reward the module lecturers and TA's by submitting your own, hard work.

 * We will regularly use the [moss](https://theory.stanford.edu/~aiken/moss/) system for detecting software similarity in assessed work. A peer reviewed publication explains [how it works](http://theory.stanford.edu/~aiken/publications/papers/sigmod03.pdf).

When the module lecturers decide that there is evidence of plagiarism, the case will be forwarded to the Senior Tutor, who will look at the evidence and apply penalties and warnings, or, if necessary, forward this to the University.

 * [University regulations on plagiarism](https://intranet.birmingham.ac.uk/as/studentservices/conduct/plagiarism/index.aspx).

## Student Welfare

If you miss or are going to miss an assessed test, or are getting behind the learning material, for reasons outside of your control, please contact [Student Welfare](https://coss.formstack.com/forms/college_wellbeing_appointments).

Occasionally students represent the University in sports, programming competitions, etc., and if this is going to affect your ability to comply with a deadline, the Welfare Team should be informed.

It is the Welfare Team, not the teaching team, who can award extensions and cancellations, and devise other measures to cope with adverse situations. It is important to contact Welfare as soon as possible when such situations arise.

<a name="calendar"></a>
## <a id="Calendar"></a>Calendar

```
   September 2023
Su Mo Tu We Th Fr Sa
24 25 26 27 28 29 30  Week  1

   October 2023
Su Mo Tu We Th Fr Sa
 1  2  3  4  5  6  7  Week  2
 8  9 10 11 12 13 14  Week  3
15 16 17 18 19 20 21  Week  4
22 23 24 25 26 27 28  Week  5  Practice test
29 30 31  1  2  3  4  Week  6  Consolidation Week

   November 2023
Su Mo Tu We Th Fr Sa
 5  6  7  8  9 10 11  Week  7  Test 1 (40%)
12 13 14 15 16 17 18  Week  8
19 20 21 22 23 24 25  Week  9
26 27 28 29 30  1  2  Week 10

   December 2023
Su Mo Tu We Th Fr Sa
 3  4  5  6  7  8  9  Week 11  Test 2 (60%)
```

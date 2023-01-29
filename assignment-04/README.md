[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=9565583&assignment_repo_type=AssignmentRepo)
# Introduction to FP and Scala
## Assignment 4 - IO
Provide your own implementation of a subset of `IO` functionality. 
It should be stack safe: sequencing of `IO`s should not blow the stack. 
Do not bother with asynchronicity, this `IO` should be executed in a synchronous manner.
Do not forget about tests on each method.
Do not bother with the "world" stuff, focus on implementation from the second half of the lab.
As the task at hand is pretty hard, the grading will be more relaxed and you will have 2 weeks to complete the task (extensions are negotiable).
This assignment will weight twice as much as a regular homework.

As a fist step to solving the task, I would recommend focusing on map and flatMap implementation from the lab. You will need to think of IO as a "boxing" for your computations, that does nothing, but tells you what computations should be made. Beware, that stack safety is a must. As a recommendation, firstly, focus on just making an IO that works. Don't bother about stack safety until you provide an implementation of all functionality (as you will probably get a big brain, trying to make it stack safe from the scratch. Hardcore guys can ignore this tip). As an approach to make IO stack safe, try googling "scala trampoline". There is a great article about how to make it for State monad. Try and make the same stuff for the regular IO without state. As a reminder, you are not allowed to use try-catch in your code, there is a sealed trait Try imported for you, make use of it. Address your questions (if any) to a scala telegram group so that all could benefit from the answers.

Meaning of some functions:
* `*>[B](another: IO[B]): IO[B]` sequences this `IO` with `another` and returns the latter
* `as[B](newValue: => B): IO[B]` replaces inner value of this `IO` with `B`
* `attempt` Materializes any sequenced exceptions into value space, where they may be handled
* `option` replaces failures in this `IO` with None
* `redeem` Returns a new value that transforms the result of the source, given the recover or map functions, which get executed depending on whether the result ends in error or if it is successful
* `redeemWith` same as `redeem`, but for side effectful transformations
* `raiseError` returns failed `IO`
* `raiseUnless` returns `raiseError` when `cond` is `false`, otherwise `IO.unit`
* `raiseWhen` returns `raiseError` when `cond` is `true`, otherwise `IO.unit`
* `unlessA` returns the given argument if `cond` is `false`, otherwise `IO.Unit`
* `whenM` returns the given argument if `cond` is `true`, otherwise `IO.Unit`

To get deeper understanding of each method and gain some insides about implementation refer to:
* https://typelevel.org/cats-effect/docs/concepts
* https://typelevel.org/cats-effect/api/3.x/cats/effect/IO.html
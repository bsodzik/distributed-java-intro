## Labs 0 - Introduction ##

### Goal ###
- Get familiar with course plan and rules
- Configure GIT
- Configure Maven


### Realization ###

#### Course plan ####

1. Java threads
  - Introduction
  - Data synchronization
  - Concurrent collections
2. Java Messaging System (JMS)
3. REST services
4. Actor model in Akka
5. In-memory data grid - Hazelcast

#### Course rules ####
There will be a project to implement at home after each section. There will be two weeks to implement the project. Final grade will be the mean of all grades.

There will be possibility to gain additional points (positive or negative) for being active or distracting during labs.

Presence will be verified. Student can miss two labs without giving a reason. However, he might be asked by instructor to deliver exercises that were solved on labs during his absence.

#### Configure GIT ####

1. Go to [https://github.com/](https://github.com/) and create account.
2. Setup Git on your workstation
  - Follow instructions at [https://help.github.com/articles/set-up-git](https://help.github.com/articles/set-up-git)
  - Remember to use **your full name** for user name
3. Fork main repository [https://github.com/bsodzik/distributed-java-intro](https://github.com/bsodzik/distributed-java-intro)
  - Follow instructions at [https://help.github.com/articles/fork-a-repo](https://help.github.com/articles/fork-a-repo)
  - Add upstream repository with `git remote add upstream https://github.com/bsodzik/distributed-java-intro.git`
  - Remember to synchronize your fork before each labs `git pull upstream master`
4. Clone forked project using
  - `git clone https://github.com/<your_login>/distributed-java-intro.git`
5. Get familiar with basic Git commands [http://git-scm.com/docs](http://git-scm.com/docs)
  - `git clone` - clone repository
  - `git status` - check status of your project
  - `git pull` - fetch changes from remote repository and merge them with your local repository
  - `git add` - add untracked files to commit
  - `git commit -m "Your message"` - commit tracked files to your **local** repository
  - `git push origin master` - push commits to remote repository 
6. Change `README.md` file (you may write some joke) and then commit your change and push it to remote repository

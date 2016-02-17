# Journal

Document project progress

[Time Log](TimeLog.md)

### Week 1

1/18/2016, 1/21/2016 
Completed:
 * Much ado about the wrong project

1/22/2016
Completed:
 * Debugging RVM; will document later for ExC1

1/26/2016
Completed:
 * Created this repository 
 * Wrote the problem statement
 * Started documenting the project plan
 * Began listing technologies and how they fit in this project

2/09/16
Completed:
 * Implemented SantaInAnElevator primary logic class, with jUnit
 * Researched Cobertura - a code coverage tool for Java
    * The documentation here was a bit lacking. There's not much to really say
      about it, though. See the Makefile for a working example.

2/12/16
Completed:
 * Finish SantaInAnElevator
 * Work on bringing various course files up to date

2/17/16
    Did some research on Ant vs Make. I'm sticking with Make for now.  XML has
    never suited me, and it looks as though some of the major shortcomings
    I was seeing can be overcome without much hackery. I spent some time
    working on that, which did require improving my knowlege of Makefile syntax
    and builtins. The resulting file could use some love, but will work for now.

    Next, I gave some thought as to how I wanted to handle database and web
    server deployment across a variety of development and production
    environments. For the database, I settled on using my primary database
    server for all environments, with one database for production and one for
    testing - this will always be accessible behind my firewall (where the
    relevant web servers live), from any machine, so I won't need to worry
    about whether the contents of my databases are current. For the web server,
    I decided that the same server environment and TomEE+ instance could serve
    both production and testing by using different deployment roots. This may
    not be best practice for actual production, but for a school project it
    seems fine. The web server runs in a zone that is dedicated to this class
    anyway.

    After that, I spent some time drawing up a database description, and
    implementing it on my postgres server, along with some supporting roles.
    I also drew up basic wireframes for two pages of the website.

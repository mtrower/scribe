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

### Week 2

1/26/2016
Completed:
 * Created this repository 
 * Wrote the problem statement
 * Started documenting the project plan
 * Began listing technologies and how they fit in this project

### Week 4

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

### Week 5

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

### Week 10

3/30/16
3/31/16

Spent a lot of time learning about and implementing Maven.  It's supposed
to make things easier, but I'm worried about whether the boilerplate and
other overhead will be worth it - especially with the reduction in
flexibility.  I suppose time will tell.

### Week 12

4/10/16
4/11/16

Maven has been working out. It isn't fast, but at least it hasn't broken.
Maintenance cost has been low. Dependency management should prove a boon.
Already I can see flexibility issues - the Tomcat plugin does not support
multiple servers. Fortunately it's simple enough to type a curl command.

Jackson has its perks, but sometimes it seems simpler to just use
JsonGenerator. The generator feels almost like writing native JSON.  The
syntax carries some small additional bulk, but that's Java for you. People
don't come to this language for sleek lightweight syntax.

4/16/16

Alright, Maven is serving me well.

Spent some time researching various topics - git subproject structure, Java
threading, JUnit testing (had some version incompatibilities here, and
documentation on necessary imports could be better).  That's a perk I can
add at a later date.  Threading is far easier than I expected.  I suppose
Java *was* considered a major advancement in productivity in it's day.

The plugin model isn't going to be too bad, though I think I'll hold off on
dynamic loading for now.  Keeping to basic functionality, *scribe* ought to
be finishable in another day's work.

As a side note, Cobertura was painless to implement using Maven.

### Week 13

4/18/16

Maven helped me discover a bug today.  It wasn't anything Maven was intended to
do, so this is anecdotal rather than any sort of recommendation.  Maven
exec:java goal waits for all threads to finish (while CLI java invocation exits
as soon as the main thread is finished).  This eventually led me to discover
that I wasn't closing some process streams.

Log4j2 seems a complicated beast.  I'm only half sure what much of the config
does.  Or rather, it makes a reasonable amount of sense when I look at it, but
not enough sense for me to adjust it to my needs.

I moved a lot of code out into new classes today.  This cut down on code
duplication and greatly aided unit testing.  Code coverage roughly doubled.
I have some thoughts on further reducing duplicate code through inheritance,
but it will depend on the Java implementation. 

It seems I need to perform a clean before running Cobertura.  Failing to do so
results in inflated line numbers in code paths.

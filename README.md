# PoolManger_Compute
A project in my university


further details until later sections. Three main entities are to be distinguished in the system:
a Feeder,
a PoolManager, and
a PoolThread.
Roughly speaking, The Feeder is a thread that loads tasks into the PoolManager which
is an entity operating in a separate thread. The PoolManager maintains a fixed number of
PoolThreads and is responsible to distribute the tasks loaded into it (by the Feeder) amongst
its PoolThreads; these in turn carry out the tasks and return the results.
§1.1 The user (main) thread
To put matters into context, the intended use of this system is as follows. A user thread (here it
will be the main thread) is faced with certain calculations. In particular, it desires to calculate the
 expressionA.

for various values of n, say n1; : : : ; nk, and in addition it also desires to calculate the following
expressionB+expressionC

for various values of ` and m, say `1; : : : ; `r and m1; : : : ;mr, respectively. In this assignment we
shall implement such user threads and supply multithreaded calculations all managed by a single
pool thread manager.
As mentioned above, the user thread is faced with calculating k expressions of the form of (1.1)
and r expressions of the form (1.2). The user thread is allowed to pursue such a calculation by
adhering to the following limitations:
1. The user thread may create a single PoolManager object that will act as the thread pool
manager.
2. The user thread is allowed to create as many Feeder objects it desires and to provide those
with various computational tasks that those in turn load into the single PoolManager in the
system.
3. The various computational tasks are defined by the user thread as it sees fit; it is the only
thread that is aware of the global calculation. Feeder objects do not examine tasks and are
2
concentrated in loading their supplied tasks into the PoolManager. The PoolManager is
not aware of what tasks are given to it; its sole concern is the distribution of tasks amongst
its PoolThreads. Finally, PoolThreads merely carry out a specific calculation without
knowing its global usage.
4. The PoolManager reports partial results back to the user thread. It is the responsibility
of the user thread to keep track of those results and detect when the calculation of a given
expressions has ended.
To that end, the user thread maintains a Result entity for each expression. Each such entity
is supplied to the PoolManager by the user thread. The Result entity exposes a report
functionality that the PoolManager has to use in order to report an additional partial result
obtained by one of its PoolThreads that is relevant to the expression associated with the
Result entity.
5. Any other threads that the user thread creates cannot take part in executing tasks; this is the
role of the PoolThreads of the PoolManager.

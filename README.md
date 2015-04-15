# tcApp
We use Guava to create elegant async api. We use async http client which is a callback
based api to do http operation.

The UI is bare minimum with a button and three custom views were created for convenience.
Logging is wrapped since I prefer to keep things that might change in future to be
wrapped. Custom executors are used as typically the errors in executors are missed out and not
reported. A toast is shown if there is an error and class ToastMaker is a easy utility to achieve
the same.

Since the project was simple and I had almost no time due to prior commitments, lot of scope for
improvements is there.
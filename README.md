## Shopify Challenge

### The challenge

For mobile developer interns, Shopify posts a challenge: You’re asked to build an app that provides the user with a list of collections of products and look at any individual one in more detail.

The details of the challenge can be found [here](https://docs.google.com/document/d/1h3TFW9HhFxBVrmgd33dNrUiJx31NQFn6dpZHrbrSP-U/edit#heading=h.a2d92edyecrw).

### My approach

I aimed to build an extensible and testable architecture. Building on a data layer which is modeled on the Shopify API, I use Fragments to display the individual screens. 

Each Fragment possesses an accompanying ViewModel, which uses Retrofit and RxJava to fetch data. The ViewModels hold a ViewState, which encapsulates the current state of the view model and is pushed to Fragment via a LiveData observer. 

Interaction within a Fragment is performed reactively via Actions. Any event of interest — for instance a refresh by the user — is encapsulated as an Action. Performing this Action returns a Result, such as a fetched list of collections. The current ViewState and the Result are then reduced to create the next ViewState, which is then pushed to the Fragment.

I use Dagger 2 for dependency injection, making it easy to test individual components. For instance, by extracting string formatting functionality into a VariantsFormatter, classes like ProductsViewEntityMapper and DetailsViewModel can be tested with a MockVariantsFormatter with depending on a Context (and thus the Android framework). This enables us to write unit tests instead of instrumented tests. 

---

### Extensibility

The data flow described above makes it easy to extend the app with additional functionality. For instance, adding a database via Room to store collections and products requires only the following steps: 
1. Add the necessary class annotations, DAOs (with methods returning Observables) and a database class. The latter is then injected via Dagger. 
2. In the ViewModel, subscribe to database changes and map them to a new Action subclass, such as Action.DatabaseChange(val collections: List<Collection>).
3. Merge all emitting sources via Observable.merge(refreshRelay, databaseChanges) and subscribe to it. 
4. Extend the processAction(action) method to handle Action.DatabaseChange.
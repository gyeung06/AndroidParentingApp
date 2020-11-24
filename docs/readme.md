# HOW WE IMPLEMENTED REQUIREMENTS
## [20] Kids Portrait Photos
 - I want to be able to set an image for each of my children so I can personalize the application.
 - I want to be able to select an existing image from my phone using its gallery, so I can use a great photo I already have.
 - I want to be able to take a new photo using the phone so I can quickly get a photo if I don't already have one.
 - I want to be able to change the portrait photo of my child because over time I will need to update their image.
 - When configuring my children in the app, I want the list of children to show the picture for each child.
 - If I don't want to select an image for a new child, I would like there to be some default general image that is used.
 - It would be nice to be able to crop an image inside the application so I can select which part of an image to use (not for marks).
 - I want all data about each child to be saved between executions of the program.



## [40] Whose Turn
 - I want the app to maintain a list of tasks and help me track which one of my children get to do it next because there are always disagreements about who gets to do something first, or who's turn it is to do it.
   * a list of tasks will be shown after you click **WHOSE TURN** button in main menu
 - I want to be able to add, remove, and edit the set of tasks the program knows.
   * you can add by clicking the **+** fab, remove by click **Edit > delete** or edit it
 - I want each task to have a description, such as "First bath", or "Put pop can into can cooler".
   * Task description will needed to be provided (CANNOT have exact same text description)
 - The list of tasks should show the task name, and the name of the child whose turn it is next so I can quickly see the configured tasks.
   * implemented in an obvious way
 - Tapping on a task puts up a pop-up message (or screen) showing:
  - The description of the task
  (implemented in an obvious way)
  - Name and photo of the child whose turn it is to do this task next  (implemented in an obvious way)
  - Convenient way to confirm that the child has had their turn (and returns to the task list)  (implemented in an obvious way, it will not be shown if there is no next child)
  - Convenient way of cancelling (and returns to the task list)  (implemented in an obvious way , it will just return to the task list; interpreted this as cancel viewing the task)
  - When a child has had their turn, it automatically advances to the next child's turn for that task next time  (implemented in an obvious way)
  - I want all the tasks and whose turn it is to be saved between executions of the program so that the app can help me track whose turn it is. (implemented in an obvious way)
 > Note that if you add a new child AFTER a task is created, it will NOT be added to the task's next child queue, you will need to create a new task
 > however, if you delete a existing child, in a task that has this child, it will be deleted from the queue


## [25] Coin Flip Enhancements
 - I want to be able to override the default choice for whose turn it is to select heads or tails because sometimes one specific child must choose (such as if the flip only involves them), or if there is an external factor (such as the child losing their turn due to behaviour).
   - The child list auto selects the child whose turn it is. The user can easily select a different child on the list.
 - When I go to flip a coin, when the application is asking the current child to select heads or tails, I want a convenient way to override the current "default" choice of who gets to fip. In this case I want to be able to manually select either a child, or nobody.
   - Aside from being able to pick a different child, there is also a clear selection button to make it clear that the user can choose nobody.
 - When I go to manually select a different child, I want the app to show me a queue of my children in the order of whose turn would come first, second, third, etc. For example, the current "default" child would be first (top), and the child whose turn it was previously would be last in the list.
    - The child list is now ordered by the last time they were the chooser, with the oldest on top, and the most recent at the bottom.
 - When a coin flip happens, the child who chose heads vs tails is moved to the end of the queue for next time because they just had a turn choosing.
   - This is a result of ordering the list of children.
 - When I manually select nobody's turn, the queue of waiting children is not changed.
   - The childrens' choose time is not affected when nobody is the chooser.
 - I want the app to show me the portrait picture of my child in most places where it makes sense. For example, show their portrait when it is their turn to choose heads or tails, and when the queue of children is shown.
   - portraits are next to the name in both choose lists
 - I want all data about coin flips to be saved between executions of the program.
   - already done


## [5] Help screen
 - I want there to be a help screen which gives me facts about the application so I can know who created it.
 - State the name of the team which created the app.
 - It would be nice (but not required or worth marks) to list the names of the developers who created it.
 - I want it to show me any copyright information, such as links to material used in the app which is under copyright (such as images).


 Style guide sources
 Some elements taken from Google's style guide https://google.github.io/styleguide/javaguide.html

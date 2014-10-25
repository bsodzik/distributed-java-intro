# Charity Flea Market #

The goal of this project is to implement a multi-threaded simulation of a "Charity Flea Market". People called *donors* can bring their old junk to the market and it will be given away on *auctions*. Auctions are scheduled and led by a *chairman*. People who take part in auctions are called *recipients*. Only limited number of recipients can register for particular auction. Registration time for each auction is fixed. Once registration is closed, chairman draws a lucky recipient who gets the item. After that recipients can register for another auction and the cycle continues. Chairman can decide to finish all auctions if there are no items from donors.

There are four main roles in the charity flea market with following responsibilities

- *Market Manager* - Market manager registers donors and recipients before they can enter the market. Once everyone is registered, manager opens market doors and lets all participants in. Manager can close the market and ask participants to leave. 
- *Donor* - Donor wants to get rid of old junk by giving it to auctions.
- *Recipient* - Recipient seeks for interesting items and takes part in auctions. Once registered for auction, recipient waits for its result.
- *Chairman* - Chairman is the main role in the system. Chairman coordinates auctions, draws lucky winner and notifies recipients. Chairman can decide to close the market if there are no items from donors.

There are several rules and regulations on the market

**Market manager rules**

- Market manager allows any number of donors and recipients to register.
- Market manager opens market and lets all donors and recipients into the market (what actually means manager starts donors' and recipients' threads).
- Market manager closes market and asks all donors and recipients to leave the market (what actually means donors' and recipients' threads should finish shortly after).

**Donor rules**

- Donor must have unique name.
- Donor participates in auctions as long as market is opened.
- Donor waits between 5 to 30 seconds before registering the item.
- If the queue of registered items is full, donor waits up to 5 seconds and tries again.

**Recipient rules**

- Recipient must have unique name.
- Recipient participates in auctions as long as market is opened.
- Recipient waits up to 5 seconds before registering for auction.
- If registration for auction was successful, recipient waits for auction end.
- If auction was won by recipient, recipient waits additional 5 to 15 seconds before registering for another auction.
- Recipient stores all winning items.

**Chairman rules**

- Chairman maintains queue of registered items. Queue length is limited to 10 items.
- Chairman maintains list of registered recipients for upcoming auction. List length is limited to 10 items.
- Chairman waits exactly 5 seconds before drawing winning recipient.
- If there are no items in the queue, chairman waits additionally up to 5 seconds. If there are still no items, chairman asks market manager to close the market and stops further work.
- If there was no recipients for current item, nothing happens.
- If there was at least one recipient for current item, chairman selects a winner randomly. Chairman notifies the winner about won item and makes sure all recipients know that particular auction was finished.

**Item rules**

- Item must have unique name.

**General rules**

- There is only one market manager.
- There is only one chairman.
- There are multiple donors and recipients (at least one of each).
- Donors and recipients must be registered in the system before the market is opened.
- When market manager decides to close the market all participants must leave (all threads must finish their work).

**Logging rules**

Several events must be logged to `System.out` in order to make analysis of working program possible. Please place particular log messages in your application

- When chairman successfully registers recipient for auction: `Registering {recipientName}`.
- When chairman draws winner for item: `Winner for auction {itemName} is {recipientName}`.
- When chairman wants to draw winner but no recipients registered: `There is no winner for {itemName}`.
- When chairman decides to close market: `No auctions within 5 seconds. Closing the market`.
- When chairman thread ends: `Chairman says good bye`.
- When recipient is notified about winning the item: `{recipientName} won {itemName}`.
- When recipient thread ends: `{recipientName} says good bye leaving with items {listOfWonItemsNames}`.
- When donor thread ends: `{donorName} says good bye`.

**Additional hints**

- Experiment with corner cases, e.g. one recipient only or one donor only.
- Implement as many rules as you can. Start with simple cases and add complex ones later.
- Do not overcomplicate. For example item should be a simple object with unique name - nothing more.

**Organizational issues**

- Project should be created in *students* directory. There are no classes provided by the tutor.
- The deadline is midnight of 16th of November 2014 (16.11.2014 24:00).
- If you want to commit your solution earlier without revealing it to other students you can send it by email to [bsodzik@tlen.pl](mailto:bsodzik@tlen.pl).
- If you have general questions to the project please create new issue on github and label it as `help wanted`.
- Good luck and have fun!
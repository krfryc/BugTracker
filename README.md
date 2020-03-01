# BugTracker
 BugTracker - create, manage and handle bug tickets

Commit full version Bug Tracker v1.0
Contains the following functions:
- Project - create new projects and assign users (available for admin and project managers)

- Tickets - can be submitted by all users assigned to the project. The design idea was to avoid bugs to unintentionally leak out to different teams/projects. Safeguard code has been added to avoid unassined users hack into ticket details. Tickets can be manually assigned to specific developer by admin or project leader, also the unassigned ticket is shown as public for all other developers in projects ticket list. Upon entering the ticket the developer will be automatically assigned to that ticket.

- Registration - create new accounts (the user role will be set to default with no project assigned - cannot submitt tickets)

- Role management: admin (can access all contets of the webpage), project manager (can access all contents of webpage only for assigned procjets), developer (can access to assigned projects and tickets content) and submitter (tester - can access contents of projects and submitted contents).
- Ticket history tracker - logs all field changes made within ticket.
- File management - files are accessed and maintained in AWS S3 bucket - profile picutres and attachments for tickets.
- User profile - change password (not available in demo accounts) and profile picture 
- Charts presentation - shows ticket data depending on user and role. It will show only revelant data for the user (only Admin can see all data)

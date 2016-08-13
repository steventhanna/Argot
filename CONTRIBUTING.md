# Contributing

The easiest way to keep Argot running smoothly is to maintain a rapid development environment.  Contributions should be easy and clear to understand, whether it is in between branches, or during code review.

## Branches
The following is an overview of all of the branches used within the project.  Below is an example flow.  **Note: There are some naming changes**

![Example Flow](http://nvie.com/img/git-model@2x.png)

### feature
The feature branch(s) are individual branches with different modular features.
*Naming Convention:* `feature-$featureName$`
Feature branches will merge into master.

### master
The master branch houses the most recent changes.  **MAY NOT PASS INTEGRATION TESTS**

### release
The release branch is used for testing incremental builds of the platform. Once changes are deemed stable enough from Master, and are moved into staging. **MUST PASS INTEGRATION TESTS**.  Once in release, a new instance is automatically spun up in production data for internal testing.

### hotfix
The hotfix branch(s) are individual branches used to quickly integrate patches and fixes ASAP.
*Naming Convention:* `hotfix-$hotfixName*`
Hotfix branches merge directly into production

### stable
The stable branch is pretty self explanatory... Its the code currentlyin the latest stable build.

## Pushing Code
Commits should be made during and after incremental changes on its corresponding feature branch. Commit messages should be clear and descriptive, and should not be like any of the following at the link: [bad but funny examples](https://www.facebook.com/steventhanna/media_set?set=a.313618052177148.100005450594604&type=3).

One submitting a pull request, the PR's commits will be squashed before being merged (all commits pushed into one).

## Issues
The issues tool is used for bug tracking, feature request, and milestone tracking.

### Bug's
Bugs generated from user submissions will not automatically be inputed into Github Issues.  Eventually this will change as the quality of our internal tools increases, but for now it must be done manually.

That being said, issues should have clear and relatively short titles, with heavily documented descriptions. The description should include any steps to reproduced the problem (if it is a bug), or use cases and example software flow (for feature requests).  The more information the better.  Select the appropriate label(s) for the issue, as well as possible signees.

### Feature request's
Features are integral to the Ritmico Platform. All feature requests should include a clear title, and well documented description as to the nature of the feature, the flow in the software that it should take, and any implications on the current system or infrastructure.

Features currently being worked on with be assigned to a specific person or persons, and will receive the `In Progress` label. The assignee(s) of that feature are responsible for that feature, both in development and during deployment should any bugs arise.

## Pull Requests
Pull Request will be Argot's major form of code review.  When merging between feature and master branches, Pull Requests must pass all integration tests and other styling tests. Pull Requests must pass both the integration tests, and actual manual testing done by contributors.

Pull Requests should include a descriptive title in regards to the feature being modified, as well as a well documented description touching on what has been changed, any difficulties in the code (bugs we should keep an eye on), methods used, and any possible conflicts.  After a code review by other Argot developers, the feature branch will be merged into master.

When the feature branch is merged into master, it is the feature's assignee(s) job to ensure its behavior in the master branch and beyond.  This means maintaining  proper testing in master, and adapting the feature as demand changes.


## Adding New Languages
Language support is paramount to the success of Argot as a documentation system, and new languages will always be suggested.  In order to cover the most ground in the shortest period of time, a single language should not be added, but rather a language group. For example Java, Javascript, C++, C, etc. all use the same commenting system. Other examples of language groups include but do not limit languages using `#`, `-`, or `;`. When building additional systems, it is a lot easier on the developers part if the language system has a standardized multi-line comment system such as in the Slash Languages, as Argot in languages with multi-line systems only works when contained within multi-line comments.  All other comments are ignored, speeding up parsing and analysis.
```java
/**
*
*/
```
Methods for parsing languages that do not utilize multi-line comments is still up for discussion.

- New file name `YourLangLanguage.java`
- Must extend `Language`
- Must fill out all abstract methods required
- In `ArgotFile` in the Function `delegateLanguages`, add your language to the switch statement
- Write Tests

## Adding new Argot Types
An Argot Type is what comes after `@type :: `. In order to add a type:

- New file named `YourtypeComment.java`
- Must implement `Comment`
- Must fill out all methods required from `Comment`
- In `Language.java` add your type to the switch statement in `delegateComments`

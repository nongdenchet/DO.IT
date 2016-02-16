# Pre-work - *DO.IT* [![Build Status](https://travis-ci.org/nongdenchet/DO.IT.svg?branch=master)](https://travis-ci.org/nongdenchet/DO.IT) [![Coverage Status](https://coveralls.io/repos/github/nongdenchet/DO.IT/badge.svg?branch=master)](https://coveralls.io/github/nongdenchet/DO.IT?branch=master)

**DO.IT** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Vũ Huy Quân**

Time spent: **28** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and choose update to bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items into SQLite instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter]
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a DialogFragment instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

## Testing
* To run unit-test: switch to prodDebug or proRelease buildVariant
* All ui-test will be run under in-memory data not in SQLite database

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/nongdenchet/DO.IT/blob/master/do_it.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Challenges encountered while building the app:
  - Facing problem with WRAP_CONTENT in RecyclerView but have overcomed it

## License

    Copyright [2016] [nongdenchet]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


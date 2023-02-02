# CS622 Assignment 2 - Sean Rawson

## Requirements
Java 11 
Jackson 2.13
JUnit 5

## Running the program
The main entry point for the program is in class ```Main```. The easiest way to
try it out is to load the project in your IDE and run Main.

## Program Design
This program is designed to implement two main areas of functionality. Those
areas are file merging and file searching. JSON files are used for sample data,
so the output of any search actually contains a list of objects of type
```JsonNode```. This is one of the main return types used by Jackson when reading JSON
strings or trees.

### File Merging
File merging is accomplished by reading, line by line, through each file in a
list of files. As each line is read, it is written to the destination file.
This functionality is implement somewhat naively, with no permissions checking
or validation of file formats. Invalid formats will be caught by
the JSON processing code.

### File Searching
With JSON files used for the project's sample data, the Jackson library was used to read in json
strings and/or trees. The main class providing search functionality is
```JsonSearcher```.

The searcher will attempt to process a JSON file as a list of JSON strings, one
per line. If that method fails, it will attempt to process the file as a tree,
meaning the format of 'pretty printed' JSON.

Every ```JsonSearcher``` has several private member variables that can be
initialized via the constructor or set with the corresponding accessor methods.

```JsonSearcher.startingField``` specifies the top-level field of each
```JsonNode``` that should be considered the parent of the desired search area.
The thinking is that often JSON objects will be returned by an API with some
metadata attached to them. For example:
```
{
    "source": "APIv2",
    "url": "https://api-v2.my-site.com"
    "data": {
        "key1": "value1",
        "key2": "value2"
    }
}
```
If we're only interested in nodes that are children of the ```"data"``` node,
we can specify ```startingField``` in the ```JsonSearcher``` constructor.  If
the specified starting field doesn't exist in an object, then the entire object
will be searched instead.

```JsonSearcher.matchMethod``` specifies either ```KEYWORD``` or ```PATTERN```
matching for searches. This should be used carefully, as it can produce some
unexpected results. If ```PATTERN``` is used and the search pattern is
specified as a keyword (i.e. ```"value"``` instead of ```".*value.*"```), only
nodes with exact matches will be returned. Strings like ```"value1"``` or ```"here is a value"```
will not match.

```JsonSearcher.ignoreCase``` allows for case sensitive or case insensitive
matching. **Case sensitive** matching is enabled by default.


## Future Improvements

There are several areas that could be improved in this program.

File validation is essentially performed by the search functions, which fail
when invalid formats are encountered. The code could be refactored to move that
validation to another class. The search code would be simplified somewhat if
there was an assumption of properly formatted files.

The search capabilities are not very fine-grained currently. They could be
expanded to provide for searching only specific fields and/or searching some
fields by pattern and some fields by keyword at the same time. One example
might be searching for all projects that match a keyword and have a close date
within a certain date range.

At present, only the "funds_raised_percent" and "close_date" fields are printed
from each matching record. Allowing the user to select the output fields and
customize the output format would be valuable additions.

# Lucene App

This is a Command Line Application. It's written in java and using [Apache Lucene](https://lucene.apache.org/) library 
to perform some of it's operations such as indexing documents and searching words. 

## Features

- Indexing the documents inside the folder path we give,
- Searching a word we want,
- Storing and showing the indexed folder paths and
- Deleting the folder path from the list.

## Installation

- Download [Lucene App](https://github.com/ibrahimansar/lucene/blob/main/LuceneApp.exe) from this repository.
- Install it in your local machine.
- Add installation path in your environmental variable.
---
# Usages

### *Indexing documents*
To index the text contained in one or more documents, enter the folder path as the example given below.

```sh
LuceneApp -path C:/LuceneData/TestFolder1 
```


### *Searching words*
To search a word, enter your word followed by -search as below.

```sh
LuceneApp -search Lucene 
```


### *Printing the folder paths*
Enter -list "show" command to list all the indexed folder paths

```sh
LuceneApp -list show 
```
### *delete the  folder paths*
To delete a path from the list, enter the path to be deleted

```sh
LuceneApp -delete C:/LuceneData/TestFolder1
```
---
#### **Thanks for using our Application**

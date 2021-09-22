# Lucene

A Command Line Application where we can use [Apache Lucene](https://lucene.apache.org/) or [Elastic Search](https://www.elastic.co/)
to perform some of it's operations such as indexing text documents. 

## Features

#### Doing the following operations using any one of the methods. 
- Indexing the documents inside the folder path.
- Searching a word or a sentance from index.
- Listing the indexed folder paths and
- Deleting the index.

## Installation

- Download [Lucene](https://drive.google.com/file/d/13tF0pgzOCsqiw_v25infGev4stXBM7HU/view?usp=sharing).
- Install it in your local machine.
- Add installation path to your environmental variable.
- To use ElasticSearch, you need to install [Elastic Search](https://www.elastic.co/downloads/elasticsearch) and start it in your local machine. 
---
# Usages

### *Setting method*
First, you need to setup any one of the methods to do the operations. you can set and also switch methods by the following command. 

```sh
Lucene -method Lucene (or) ElasticSearch
```


### *Indexing documents*
To index the text documents in a folder.

```sh
Lucene -index C:/LuceneData/TestFolder1 
```


### *Searching words or sentances*
To search if a word or a sentance is in index.

```sh
Lucene -search Lucene 
```


### *Printing the folder paths*
To get indexed folder's list.

```sh
Lucene -list show 
```
### *Delete the indexed path*
To delete an indexed path from the list.

```sh
Lucene -delete C:/LuceneData/TestFolder1
```
---
#### **Thanks for using our Application**

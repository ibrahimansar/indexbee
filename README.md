# Introduction

___IndexBee___ is a Command Line Application, for windows, where we can use either [Apache Lucene](https://lucene.apache.org/) or [Elastic Search](https://www.elastic.co/) to perform operations based on indexing machanism such as complex searching. 

## Features

#### IndexBee has many features such as:
- Indexing the documents inside the folder.
- Searching a word or a sentance based on indices.
- Listing the indexed folders.
- Deleting the indexed folders.

## Installation

- Download [IndexBee](https://drive.google.com/file/d/1OYT2zk_PZBFCUMpTiTB8aynpxrps14Pr/view?usp=sharing).
- Install it in your local machine.
- Add installation path to your system variable.
- To use ElasticSearch, you need to install [Elastic Search](https://www.elastic.co/downloads/elasticsearch) and add it's installation path to your system variable if needed. 
- To start ElasticSearch, type elasticsearch in your CLI.
- Ensure the port is set to 9200.
---
# Usages

### *Setting method*
First, you need to setup any one of the methods to do the operations. you can set and also switch methods by the following command. 

```sh
indexbee -method Lucene (or) ElasticSearch
```

_Once you set the method, it is persistent and no need to set each and every time you run the application_.

### *Finding the current active method*
Use the following command to find the method, which is currently active. 

```sh
indexbee -which method
```

### *Indexing documents*
To index text documents in the folder.

```sh
indexbee -index C:/LuceneData/TestFolder1 
```


### *Searching words or sentences*
To list the name of the files that contain the results.

```sh
indexbee -search Lucene 
```


### *Printing the folder paths*
To get the list of indexed folders.

```sh
indexbee -list show 
```
### *Deleting the indexed path*
To delete an indexed folder.

```sh
indexbee -delete C:/LuceneData/TestFolder1
```
---

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Mohamed Ibrahim Ansari - mdansariibrahim1@gmail.com

Project Link: [https://github.com/ibrahimansar/lucene](https://github.com/ibrahimansar/lucene)


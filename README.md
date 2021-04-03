# DSA_graphs
An assignment given to us in our data structures course based on graphs.

### Compiling and Executing

``` bash
  cd DSA_graphs
  java A4_2019CS10359.java file1.csv file2.csv function_name
```
The function name can take 3 values:
1.) average : This will print the average number of characters each Marvel character is associated with.

2.) rank : Prints a sorted list of all characters, with comma as delimiter (only comma, as delimiter and no space). Sorting is in descending order of co-occurrence with other characters. That is, characters with more co-occurrences appear before. If there is a tie between characters based on co-occurrence count, then the order is descending based on lexicographic order of the character strings.

3.) independent_storylines_dfs : Implements DFS, then finds the independent storylines, that have no edge across them, using DFS. Then, we print the characters in each independent storyline, as a separate line in the output.
The largest storyline (with maximum characters)  appears at the top, followed by the second largest and so on. Within each line, the character names are delimited with comma, and lexicographically sorted in descending order. If two storylines have same number of characters, ties are broken in lexicographically descending order of character names.

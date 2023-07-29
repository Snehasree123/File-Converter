# FileConverter

FileConverter is a Java program that can be used to convert and normalize CSV or text files. It provides two commands - `convert` and `normalize`.

## Usage

### Convert

To convert a file, use the following command:

```
convert source.xxx destination.yyy
```

Where `source.xxx` is the path to the source file and `destination.yyy` is the path to the destination file. Both files should be either CSV or text files and their extensions should not be the same.

### Normalize

To normalize a file, use the following command:

```
normalize source.xxx
```

Where `source.xxx` is the path to the source file. The file should be either CSV or text.

### Quit

To exit the program, use the following command:

```
quit
```

## Implementation

The program reads the file from the resources folder using the `ClassLoader` and `File` classes. It then processes the file by splitting each line into cells using the comma delimiter. The `convert` command writes the processed data to a new file with the given destination path, while the `normalize` command modifies the data in place and writes it back to the source file.

The `normalize` command performs the following operations on the data:

1. If a cell is empty, it replaces it with "N/A".
2. If a cell contains a whole number, it formats it with a leading sign and 10 digits.
3. If a cell contains a decimal number, it formats it with 2 decimal places or in scientific notation.
4. If a cell contains a string longer than 13 characters, it shortens it to 10 characters and adds ellipses.

## Error Handling

The program handles the following errors:

1. If the source file does not exist or cannot be read, an error message is printed.
2. If the destination file cannot be written, an error message is printed.
3. If the command is invalid or the arguments are incorrect, an error message is printed.
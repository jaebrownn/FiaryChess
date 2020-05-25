#!/bin/bash

# Compile all the files
javac ./*.java

# Run the test boards
for testBoard in ./testBoards/*.board; do
    echo "------------------"
    echo "Running $testBoard"

    # Capture output
    OUTPUT="$(java FairyChess "$testBoard")"

    # Compare output with expected output
    EXPECTED="$(cat "$testBoard.out")"

    if [ "$OUTPUT" == "$EXPECTED" ]; then
        echo "Correct! :)"
    else
        echo "Incorrect! :("
    fi

    echo
done


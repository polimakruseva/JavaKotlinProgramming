package com.polimakruseva.gui

import com.polimakruseva.calculator.ExpressionTree
import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.*
import javax.swing.*
import kotlin.collections.HashMap


class Calculator : ActionListener, KeyListener, MouseListener {
    private inner class ListKeyListener : KeyListener {
        override fun keyTyped(e: KeyEvent?) {}

        override fun keyPressed(e: KeyEvent?) {
            if (selectedIndex != -1) {
                if (e != null) {
                    if (e.keyChar == '=') {
                        solve()
                        variables.isFocusable = false
                    } else {
                        dataModel.set(selectedIndex, dataModel.get(selectedIndex) + e.keyChar)
                    }
                }
            }
        }

        override fun keyReleased(e: KeyEvent?) {}
    }

    private var currentExpression : String = ""
    private val enterExpression = JTextArea(currentExpression).apply {
        isEditable = false
        rows = 3
        columns = 20
        font = Font("", Font.BOLD, 14)
    }
    private val frame = JFrame("Calculator").apply {
        setSize(700, 400)
        isResizable = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    }
    private val result = JLabel("Result:").apply {
        font = Font("", Font.BOLD, 14)
    }
    private val resultText = JTextArea("").apply {
        isEditable = false
        rows = 3
        columns = 20
        isFocusable = false
        font = Font("", Font.BOLD, 14)
    }
    private val namesOfDigitsButtons = listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "=", ".")
    private val namesOfSymbolsButtons = listOf("+", "-", "x", "/", "(", ")", "AC", "←")
    private val digitButtons : MutableList<JButton> = mutableListOf()
    private val symbolButtons : MutableList<JButton> = mutableListOf()

    private val buttonsPanel = JPanel().apply {
        layout = GridLayout(1, 4, 10, 10)
    }
    private val upperPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
    }

    private val digitsPanel = JPanel().apply {
        layout = GridLayout(4, 3, 10, 10)
    }
    private val symbolsPanel = JPanel().apply {
        layout = GridLayout(4, 2, 10, 10)
    }
    private val dataModel = DefaultListModel<String>()
    private val variables = JList(dataModel).apply {
        isFocusable = false
    }
    private val outputArea = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
    }
    private val depthText = JTextArea().apply {
        isEditable = false
        isFocusable = false
        lineWrap = true
        wrapStyleWord = true
        rows = 2
    }
    private val debugRepresentation = JTextArea().apply {
        isEditable = false
        isFocusable = false
        lineWrap = true
        wrapStyleWord = true
        rows = 15
    }

    private val expressionTree = ExpressionTree()

    private var wasSolvedOrException = false
    private var shiftPressed = false
    private var isWaitingForVariables = false
    private var selectedIndex = -1

    private val mapWithVariables = HashMap<String, String>()

    init {
        createButtonsAndPanels()
        buttonsPanel.add(digitsPanel)
        buttonsPanel.add(symbolsPanel)
        buttonsPanel.add(variables)

        outputArea.add(depthText)
        outputArea.add(Box.createVerticalStrut(5))
        outputArea.add(Box.createVerticalStrut(5))
        outputArea.add(debugRepresentation)

        buttonsPanel.add(outputArea)

        upperPanel.add(Box.createVerticalStrut(10))
        upperPanel.add(enterExpression)
        upperPanel.add(Box.createHorizontalStrut(10))
        upperPanel.add(result)
        upperPanel.add(Box.createHorizontalStrut(10))
        upperPanel.add(resultText)

        frame.add(upperPanel, BorderLayout.NORTH)
        frame.add(buttonsPanel, BorderLayout.CENTER)

        addActionListeners()
        enterExpression.addKeyListener(this)
        variables.addMouseListener(this)
        variables.addKeyListener(ListKeyListener())
        frame.isVisible = true
    }

    private fun addActionListeners() {
        for (button in digitButtons) {
            button.addActionListener(this)
            button.isFocusable = false
        }
        for (button in symbolButtons) {
            button.addActionListener(this)
            button.isFocusable = false
        }
    }

    private fun createButtonsAndPanels() {
        for (name in namesOfDigitsButtons) {
            digitButtons.add(JButton(name))
        }
        for (name in namesOfSymbolsButtons) {
            symbolButtons.add(JButton(name))
        }
        for (i in 0..3) {
            for (j in 0..2) {
                digitsPanel.add(digitButtons[i * 3 + j])
            }
        }
        for (i in 0..3) {
            for (j in 0..1) {
                symbolsPanel.add(symbolButtons[i * 2 + j])
            }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        val pushedButton = e?.actionCommand

        val characters = "0123456789+-x/()."
        if (pushedButton != null) {
            if (characters.findAnyOf(listOf(pushedButton)) != null) {
                clearPreviousResult()
            }
        }
        when (pushedButton) {
            "0" -> currentExpression += "0"
            "1" -> currentExpression += "1"
            "2" -> currentExpression += "2"
            "3" -> currentExpression += "3"
            "4" -> currentExpression += "4"
            "5" -> currentExpression += "5"
            "6" -> currentExpression += "6"
            "7" -> currentExpression += "7"
            "8" -> currentExpression += "8"
            "9" -> currentExpression += "9"
            "+" -> currentExpression += "+"
            "-" -> currentExpression += "-"
            "x" -> currentExpression += "*"
            "/" -> currentExpression += "/"
            "(" -> currentExpression += "("
            ")" -> currentExpression += ")"
            "←" -> {
                if (currentExpression.length == 1) {
                    clearPreviousResult()
                }
                currentExpression = currentExpression.dropLast(1)
            }
            "=" -> solve()
            "AC" -> {
                clearPreviousResult()
            }
            "." -> currentExpression += "."
        }
        println(currentExpression.length)
        enterExpression.text = currentExpression
    }

    private fun solve() {
        if (isWaitingForVariables) {
            updateMapWithVariables()
            val result = expressionTree.solveFromCalculator(mapWithVariables, dataModel.size())
            if (expressionTree.throwsException() && expressionTree.exceptionText == "Initialize variables") {
                resultText.text = expressionTree.exceptionText
            } else if (expressionTree.throwsException()) {
                isWaitingForVariables = false
                wasSolvedOrException = true
                resultText.text = expressionTree.exceptionText
            } else {
                isWaitingForVariables = false
                resultText.text = "\n" + result.toString()
                wasSolvedOrException = true
                addAdditionalInformation()
            }
        } else {
            expressionTree.loadNewExpression(currentExpression)
            if (expressionTree.throwsException()) {
                resultText.text = expressionTree.exceptionText
            } else {
                val listOfVariables = expressionTree.variables
                createListOfVariables()
                if (listOfVariables.isNotEmpty()) {
                    isWaitingForVariables = true
                    resultText.text = "Initialize variables"
                    if (inArea) {
                        variables.isFocusable = true
                    }
                } else {
                    val result = expressionTree.solveFromCalculator(mapWithVariables, dataModel.size())
                    if (expressionTree.throwsException()) {
                        resultText.text = expressionTree.exceptionText
                    } else {
                        resultText.text = "\n" + result.toString()
                        addAdditionalInformation()
                    }
                }
            }
            if (!isWaitingForVariables) {
                wasSolvedOrException = true
            }
        }
    }

    private fun addAdditionalInformation() {
        depthText.text = "Depth: " + expressionTree.treeDepth
        debugRepresentation.text = "Debug representation: \n\n" + expressionTree.treeRepresentation
    }

    private fun createListOfVariables() {
        val variablesInExpression = expressionTree.variables
        mapWithVariables.clear()
        for (variable in variablesInExpression) {
            val index = dataModel.size()
            dataModel.add(index, "$variable = ")
        }
    }

    private fun updateMapWithVariables() {
        for (i in 0 until dataModel.size()) {
            val currentElement = dataModel.getElementAt(i)
            val index = currentElement.indexOf('=')
            if (index != currentElement.length - 2) {
                mapWithVariables[currentElement.substring(0, currentElement.indexOf(' '))] =
                    currentElement.substring(index + 2)
            }
        }
    }

    private fun clearPreviousResult() {
        if (wasSolvedOrException) {
            wasSolvedOrException = false
            currentExpression = ""
            enterExpression.text = currentExpression
            resultText.text = ""
            depthText.text = ""
            debugRepresentation.text = ""
            dataModel.clear()
            variables.clearSelection()
            mapWithVariables.clear()
        }
    }

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        val letters = "qwertyuiopasdfghjklzxcvbnm"
        if (e != null) {
            if (letters.contains(e.keyChar)) {
                clearPreviousResult()
                currentExpression += e.keyChar
            }
        }
        if (e != null) {
            println(e.keyChar)
        }
        when(e?.keyCode) {
            KeyEvent.VK_1 -> {
                clearPreviousResult()
                currentExpression += "1"
            }
            KeyEvent.VK_NUMPAD1 -> {
                clearPreviousResult()
                currentExpression += "1"
            }
            KeyEvent.VK_2 -> {
                clearPreviousResult()
                currentExpression += "2"
            }
            KeyEvent.VK_NUMPAD2 -> {
                clearPreviousResult()
                currentExpression += "2"
            }
            KeyEvent.VK_3 -> {
                clearPreviousResult()
                currentExpression += "3"
            }
            KeyEvent.VK_NUMPAD3 -> {
                clearPreviousResult()
                currentExpression += "3"
            }
            KeyEvent.VK_4 -> {
                clearPreviousResult()
                currentExpression += "4"
            }
            KeyEvent.VK_NUMPAD4 -> {
                clearPreviousResult()
                currentExpression += "4"
            }
            KeyEvent.VK_5 -> {
                clearPreviousResult()
                currentExpression += "5"
            }
            KeyEvent.VK_NUMPAD5 -> {
                clearPreviousResult()
                currentExpression += "5"
            }
            KeyEvent.VK_6 -> {
                clearPreviousResult()
                currentExpression += "6"
            }
            KeyEvent.VK_NUMPAD6 -> {
                clearPreviousResult()
                currentExpression += "6"
            }
            KeyEvent.VK_7 -> {
                clearPreviousResult()
                currentExpression += "7"
            }
            KeyEvent.VK_NUMPAD7 -> {
                clearPreviousResult()
                currentExpression += "7"
            }
            KeyEvent.VK_NUMPAD8 -> {
                clearPreviousResult()
                currentExpression += "8"
            }
            KeyEvent.VK_NUMPAD9 -> {
                clearPreviousResult()
                currentExpression += "9"
            }
            KeyEvent.VK_NUMPAD0 -> {
                clearPreviousResult()
                currentExpression += "0"
            }
            KeyEvent.VK_BACK_SPACE -> {
                if (currentExpression.length == 1) {
                    clearPreviousResult()
                }
                currentExpression = currentExpression.dropLast(1)
            }
            109 -> {
                clearPreviousResult()
                currentExpression += "-"
            }
            107 -> {
                clearPreviousResult()
                currentExpression += "+"
            }
            106 -> {
                clearPreviousResult()
                currentExpression += "*"
            }
            111, 47 -> {
                clearPreviousResult()
                currentExpression += "/"
            }
            110 -> {
                clearPreviousResult()
                currentExpression += "."
            }
            46 -> {
                clearPreviousResult()
                currentExpression += "."
            }
            KeyEvent.VK_SHIFT -> shiftPressed = true
            57 -> {
                clearPreviousResult()
                currentExpression += if (shiftPressed) {
                    "("
                } else {
                    "9"
                }
            }
            48 -> {
                clearPreviousResult()
                currentExpression += if (shiftPressed) {
                    ")"
                } else {
                    "0"
                }
            }
            61 -> {
                if (shiftPressed) {
                    clearPreviousResult()
                    currentExpression += "+"
                } else {
                    if (!wasSolvedOrException) {
                        solve()
                    }
                }
            }
            45 -> {
                clearPreviousResult()
                currentExpression += "-"
            }
            56 -> {
                clearPreviousResult()
                currentExpression += if (shiftPressed) {
                    "*"
                } else {
                    "8"
                }
            }
        }
        enterExpression.text = currentExpression
    }

    override fun keyReleased(e: KeyEvent?) {
        if (e != null && e.keyCode == KeyEvent.VK_SHIFT) {
            shiftPressed = false
        }
    }

    override fun mouseClicked(e: MouseEvent?) {
        if (e != null) {
            if (e.clickCount == 1) {
                selectedIndex = variables.selectedIndex
            }
        }
    }

    override fun mousePressed(e: MouseEvent?) {}

    override fun mouseReleased(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {
        inArea = true
        variables.isFocusable = true
    }

    override fun mouseExited(e: MouseEvent?) {
        inArea = false
        selectedIndex = -1
        variables.isFocusable = false
    }

    private var inArea = false
}

fun main() {
    val calculator = Calculator()
}
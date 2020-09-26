# Logic-E-Learning-Assistant
## Description
Logic E-Learning Assistant is a tool designed to help in the study of logic. It offers a variety of functionalities for both Propositional Logic and First Order Logic.

The application was mainly developed using Java, but it also includes a python script used for evaluating First Order Logic formulas.

## Usage
First off, we select the desired type of logic and the desired chapter.
<p align="center">
  <img src="/images/main_menu.PNG" width="350" title="main menu">
   <img src="/images/chapters.PNG" width="350" title="main menu">
</p>

### Formula Analysis

The formula will be provided as input and then we select the type of info wanted. The result will be displayed in the console below the buttons.
<p align="center">
  <img src="/images/formula_analysis.PNG" width="450" title="main menu">
</p>

Evaluating First Order Logic or checking their satisfiability are special cases, they also require some text files that contain addition information as input.
<p align="center">
  <img src="/images/fol_formula_evaluation.PNG" width="450" title="main menu">
</p>
We can also see if a substitution exists for two First Order Logic formulas.
<p align="center">
  <img src="/images/fol_substitution.PNG" width="450" title="main menu">
</p>

### Natural Deduction

We must first provided some information, the initial hypotheses and the goal sequece.
<p align="center">
  <img src="/images/natural_deduction_prepare.PNG" width="450" title="main menu">
</p>

After that we can began writing our proof, each inference rule is represented by a button and the below them are the input fields for the parameters that the inference rule may require. The info button will provide more information about the inference rules and the export button will export the obtained proof as a txt file. Rules cannot be applied after the goal sequence was reached.
<p align="center">
  <img src="/images/natural_deduction.PNG" width="450" title="main menu">
</p>

A proof can be checked by selecting the "Check Proof" option the "Natural Deduction" submenu. The proof can be either provided as a txt file or writted in the console.
<p align="center">
  <img src="/images/check_proof.PNG" width="450" title="main menu">
</p>

### Normal Forms

The supported normal forms are :
- Conjunctive Normal Form and Disjunctive Normal Form for Propositional Logic
- Prenex Normal Form, Skolem Normal Form and Skolem Clausal Normal Form for First Order Logic

The way o work is fairly similar between these normal forms, so we will take CNF for Proposition Logic as an example.

We must first input a formula and select "Set current formula". The formula will be saved. After that, the input field will be used to write the subformulas that we wish to apply the transformation rules on. We can check at any time if the current formula is in FNC. If we get stuck, we can automatically generate a transformation that will be stored in a text file.
<p align="center">
  <img src="/images/fnc_rules.PNG" width="350" title="main menu">
   <img src="/images/normal_forms.PNG" width="350" title="main menu">
</p>

### Resolution

Before we can begin the proof we must provide a formula. The application will check if the formula is in FNC and will not allow it to be set if it is not. The premises for the proof will be automatically generated after that. We apply resolution between two clauses by selecting the clauses index and providing the (positive) literal. 

The proof cand be exported as a text file after that, it can also be automatically generated if it exists. This component also features a "Check Proof" component that is very similar to the one from Natural Deduction.
<p align="center">
  <img src="/images/resolution.PNG" width="450" title="main menu">
</p>

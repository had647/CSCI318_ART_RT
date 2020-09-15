var logger = {
  write: function(input){
    //document.getElementById("console-output").value += "===" + (new Date()).toISOString() + "===" + "\n" + input + "\n\n";
	document.getElementById("console-output").value += input + "\n\n"; // Changed the output style because the date was flooding the page.

  },
  clear: function(){
    document.getElementById("console-output").value = "";
  }
}



//For generating random objects
var Colours = ["Blue", "Red", "Green"];
var Types = ["Cat", "Dog", "Bird"];
var Names = ["James", "Tim", "Bob"];
var Diets = ["Rats", "Birdseed", "Apples"];
var OwnerNames = ["Jim", "Jane", "Bryce"];

//This is just used for testing (not actually used in algorithm)
function getDistance(object_x, object_y) {
    let max_distance = 10;
    let distance = max_distance;

    if (object_x.Colour == object_y.Colour) distance--;
    if (object_x.Type == object_y.Type) distance--;
    if (object_x.Name == object_y.Name) distance--;
    if (object_x.Diet == object_y.Diet) distance--;
    if (object_x.OwnerName == object_y.OwnerName) distance--;

    if ((object_x.NumberOfLegs < 4 && object_y.NumberOfLegs < 4) || (object_x.NumberOfLegs == 4 && object_y.NumberOfLegs == 4) || (object_x.NumberOfLegs > 4 && object_y.NumberOfLegs > 4)) distance--;
    if ((object_x.Age < 10 && object_y.Age < 10) || (object_x.Age == 10 && object_y.Age == 10) || (object_x.Age > 10 && object_y.Age > 10)) distance--;
    if ((object_x.NumberOfEyes < 2 && object_y.NumberOfEyes < 2) || (object_x.NumberOfEyes == 2 && object_y.NumberOfEyes == 2) || (object_x.NumberOfEyes > 2 && object_y.NumberOfEyes > 2)) distance--;
    if ((object_x.Height < 40 && object_y.Height < 40) || (object_x.Height == 40 && object_y.Height == 40) || (object_x.Height > 40 && object_y.Height > 40)) distance--;
    if ((object_x.Weight < 1000 && object_y.Weight < 1000) || (object_x.Weight == 1000 && object_y.Weight == 1000) || (object_x.Weight > 1000 && object_y.Weight > 1000)) distance--;

    return distance
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min); //The maximum is exclusive and the minimum is inclusive
}

//Generates a random Pet object. This will most likley be used with a Oracle. Not from an object heap.
function getRandomPet() {
    var Pet_0 = {
        Colour: Colours[getRandomInt(0, 3)],
        Type: Types[getRandomInt(0, 3)],
        Name: Names[getRandomInt(0, 3)],
        Diet: Diets[getRandomInt(0, 3)],
        OwnerName: OwnerNames[getRandomInt(0, 3)],
        NumberOfLegs: getRandomInt(0, 9), // <4, 4, >4          range(0-8) inclusive          
        Age: getRandomInt(0, 21), //<10, 10, >10        range(0-20)
        NumberOfEyes: getRandomInt(0, 5), //<2, 2, >2       range(0-4)
        Height: getRandomInt(0, 81), //<40, 40, >40                   range(0-80)
        Weight: getRandomInt(0, 2001), //<1000, 1000, >1000                range(0-2000)
    };

    return Pet_0
}

//This is used for testing
function displayPetConsole(Pet) {
    logger.write(Pet.Colour);
    logger.write(Pet.Type);
    logger.write(Pet.Name);
    logger.write(Pet.Diet);
    logger.write(Pet.OwnerName);
    logger.write(Pet.NumberOfLegs);
    logger.write(Pet.Age);
    logger.write(Pet.NumberOfEyes);
    logger.write(Pet.Height);
    logger.write(Pet.Weight);
}

//Generated 10 random candiates. 
var candidates = [];

function generateCandiates() {
    var i;
    for (i = 0; i < 10; i++) candidates[i] = getRandomPet();
}

//This creates S set for a candidate (see Article 2 for knowledge on S set)
function returnObjectChoicePosition(pet) {
    var categoriesAndChoices = [];

    var i;
    for (i = 0; i < 40; i++) categoriesAndChoices[i] = 0;
    var colour_index = Colours.indexOf(pet.Colour);
    categoriesAndChoices[0 + (colour_index + 1)] = 1;

    var type_index = Types.indexOf(pet.Type);
    categoriesAndChoices[4 + (type_index + 1)] = 1;

    var name_index = Names.indexOf(pet.Name);
    categoriesAndChoices[8 + (name_index + 1)] = 1;

    var diet_index = Diets.indexOf(pet.Diet);
    categoriesAndChoices[12 + (diet_index + 1)] = 1;

    var owner_name_index = OwnerNames.indexOf(pet.OwnerName);
    categoriesAndChoices[16 + (owner_name_index + 1)] = 1;

    var number_of_legs_index;
    if (pet.NumberOfLegs < 4) number_of_legs_index = 0;
    else if (pet.NumberOfLegs == 4) number_of_legs_index = 1;
    else if (pet.NumberOfLegs > 4) number_of_legs_index = 2;
    categoriesAndChoices[20 + (number_of_legs_index + 1)] = 1;

    var age_index;
    if (pet.Age < 10) age_index = 0;
    else if (pet.Age == 10) age_index = 1;
    else if (pet.Age > 10) age_index = 2;
    categoriesAndChoices[24 + (age_index + 1)] = 1;

    var number_of_eyes_index;
    if (pet.NumberOfEyes < 2) number_of_eyes_index = 0;
    else if (pet.NumberOfEyes == 2) number_of_eyes_index = 1;
    else if (pet.NumberOfEyes > 2) number_of_eyes_index = 2;
    categoriesAndChoices[28 + (number_of_eyes_index + 1)] = 1;

    var height_index;
    if (pet.Height < 40) height_index = 0;
    else if (pet.Height == 40) height_index = 1;
    else if (pet.Height > 40) height_index = 2;
    categoriesAndChoices[32 + (height_index + 1)] = 1;

    var weight_index;
    if (pet.Weight < 1000) weight_index = 0;
    else if (pet.Weight == 1000) weight_index = 1;
    else if (pet.Weight > 1000) weight_index = 2;
    categoriesAndChoices[36 + (weight_index + 1)] = 1;

    return categoriesAndChoices;
}

//Calculates the distance between the candidate S set and the 'main' S set
function calculate_sum_distance(n, S, S_temp) {
    var choice_1;
    var choice_2;
    var choice_3;
    var choice_4;
    var choice_5;
    var choice_6;
    var choice_7;
    var choice_8;
    var choice_9;
    var choice_10;

    var i;
    for (i = 0; i < 4; i++)
        if (S_temp[i] == 1) choice_1 = S[i];
    for (i = 4; i < 8; i++)
        if (S_temp[i] == 1) choice_2 = S[i];
    for (i = 8; i < 12; i++)
        if (S_temp[i] == 1) choice_3 = S[i];
    for (i = 12; i < 16; i++)
        if (S_temp[i] == 1) choice_4 = S[i];
    for (i = 16; i < 20; i++)
        if (S_temp[i] == 1) choice_5 = S[i];
    for (i = 20; i < 24; i++)
        if (S_temp[i] == 1) choice_6 = S[i];
    for (i = 24; i < 28; i++)
        if (S_temp[i] == 1) choice_7 = S[i];
    for (i = 28; i < 32; i++)
        if (S_temp[i] == 1) choice_8 = S[i];
    for (i = 32; i < 36; i++)
        if (S_temp[i] == 1) choice_9 = S[i];
    for (i = 36; i < 40; i++)
        if (S_temp[i] == 1) choice_10 = S[i];

    var sum_distance = (n - choice_1) + (n - choice_2) + (n - choice_3) + (n - choice_4) + (n - choice_5) +
        (n - choice_6) + (n - choice_7) + (n - choice_8) + (n - choice_9) + (n - choice_10);

    return sum_distance;
}


//Example objects used for testing
/*
 var Pet_0 = {
  Colour: "Red",        
  Type: "Cat",         
  Name: "Tim",        
  Diet: "Birdseed",     
  OwnerName: "Bryce",          
  NumberOfLegs: 4,               //<4, 4, >4            
  Age: 11,                       //<10, 10, >10
  NumberOfEyes: 2,               //<2, 2, >2 
  Height: 28,                    //<40, 40, >40
  Weight: 900,                   //<1000, 1000, >1000 
};

var Pet_1 = {
  Colour: "Red",       
  Type: "Cat",          
  Name: "Tim",        
  Diet: "Birdseed",     
  OwnerName: "Bryce",        
  NumberOfLegs: 4,      
  Age: 11,         
  NumberOfEyes: 2,           
  Height: 30,            
  Weight: 1000,             
};*/

/*This is how the distance is calculated between the two objects*/
/*Need to work out how to compensate for the circumstance that one object might not have the same properties as another*/
/*e.g. If Pet_0 has a property 'Name', but Pet_1 does not. Then the distance btween them woudl be increased by 1*/



var S = [];
var k;
for (k = 0; k < 40; k++) S[k] = 0; //init S set

var E = []; //past test cases
var test_case; //is initialized by either the first test case or the candidate with the largest distance

var n = 0;

var g; //for loop needs to be replaced with while(condition- failed test case)
for (g = 0; g < 60000; g++) {
    n++; //dont ask me why n is incremented here rather than at the end of the loop... the paper told me to do it. 

    if (n == 1) test_case = getRandomPet();
    else {
        generateCandiates(); //generate candidates

        var max_sum = -1;
        var current_sum;
        var candidate;
        var candidate_S;
        var max_candidate_index = -1;

        var j;
        for (j = 0; j < candidates.length; j++) { //calculating the candidate with teh max distance between it and the S set
            candidate = candidates[j];
            candidate_S = returnObjectChoicePosition(candidate);
            current_sum = calculate_sum_distance(n - 1, S, candidate_S);

            if (current_sum > max_sum) {
                max_sum = current_sum;
                max_candidate_index = j;
            }
        }
        test_case = candidates[max_candidate_index];
    }

    E[n - 1] = test_case;

    var i; //incrementing S set accordingly
    var S_test_case = returnObjectChoicePosition(test_case);
    for (i = 0; i < S.length; i++)
        if (S_test_case[i] == 1) S[i]++;

}

logger.write("done");


/*  
	This function was just testing how to generate many combinations of given strings.
	Uncomment to see how it works. If no use for it, it can be deleted.
	
	  size is the amount of letters you want there to be in each PET.
      letters is the type of characters that we want to use.
      
      eg.
      petGenerator(3, "abc");
      
      This should return an array of size 27 with all possible combinations.  
   
	
function petGenerator(size, letters) {
    var results = [];

    function recurse(cache) {
        for (var i = 0; i < letters.length; i++) {
            cache += letters[i];
            if (cache.length === size) {
                results.push(cache);
            } else {
                recurse(cache);
            }
            cache = cache.slice(0, -1);
        }
    }
    recurse(""); // Call recursive function with empty string.
    return results;
};

logger.write(petGenerator(3, "abc"));
*/


  
// Just using the same values made at the top of the page.
let petGeneratorPool = [ 
    { key: 'Types', vals: [ 'Cat', 'Dog', "Bird" ] },
    { key: 'Colors', vals: [ 'Blue', 'Red', 'Green' ] },
    { key: 'Names', vals: [ 'James', 'Tim', "Bob" ] },
	{ key: 'Diets', vals: [ 'Rats', 'Birdseed', "Apples" ] },
	{ key: 'NumberOfLegs', vals: [ '<4', '4', ">4" ] } ,
	{ key: 'Age', vals: [ '<10', '10', ">10" ] } ,
	{ key: 'NumberOfEyes', vals: [ '<2', '2', ">2" ] } ,
	{ key: 'Height', vals: [ '<40', '40', ">40" ] } ,	// in centimeters?
	{ key: 'Weight', vals: [ '<1000', '1000', ">1000" ] }  // in grams?
  ]

function generateAllPetCombinations(petGeneratorPool) {
    if (petGeneratorPool.length === 0) {
      return [[]]
    }
    let [currentPet, ...restOfPets] = petGeneratorPool // The "..." is rest parameters. Just means it can take as many arguments as we want, so it gets all the values.
    let petCombinations = generateAllPetCombinations(restOfPets)
	

    /* This function will go through each of the attributes inside of the vals part of any current pet that we are looking at 
    based on the pool, this will then reduce and map each value in each attribute into an array then return that as a Pet*/
    return currentPet.vals.reduce((a, string) => 
        [ ...a, ...combinations.map(c => [string, ...c])], [])

}

function displayPETsGenerated() {
	
	let everyPetCombination = generateAllPetCombinations(petGeneratorPool)

	logger.write("Amount of PETs Generated: " + everyPetCombination.length);

	for(var i = 0; i < 1000; i++) // You can try everyPetCombination.length but it will take a while to load all so I put 1000 just for display purposes. 
	{
		logger.write("PET" + i + "(" + everyPetCombination[i] + ")");
	}

}

displayPETsGenerated();





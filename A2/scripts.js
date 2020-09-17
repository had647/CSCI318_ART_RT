// Just using the same values made at the top of the page.
const attributePool = {
    type: ['Cat', 'Dog', "Bird"],
    color: ['Blue', 'Red', 'Green'],
    name: ['James', 'Tim', "Bob"],
    diet: ['Rats', 'Birdseed', "Apples"],
    owner: ['Jim', 'Jane', "Bryce"],
    numLegs: ['<4', '4', ">4"],
    age: ['<10', '10', ">10"],
    numEyes: ['<2', '2', ">2"],
    height: ['<40', '40', ">40"],	// in centimeters?
    weight: ['<1000', '1000', ">1000"]  // in grams?
};

//Generated 10 random candiates. 
let state = {
    candidates: [],
    petList: []
};

let logger = {
    write: function (input) {
        //document.getElementById("console-output").value += "===" + (new Date()).toISOString() + "===" + "\n" + input + "\n\n";
        document.getElementById("console-output").value += input + "\n\n"; // Changed the output style because the date was flooding the page.

    },
    writeError: function (input) {
        document.getElementById("console-output").value += "ERROR: " + input + "\n\n";
    },
    clear: function () {
        document.getElementById("console-output").value = "";
    }
};

function displayPETsGenerated() {
    if (state.petList.length === 0) {
        console.warn(`There is currently no data in the state.petList array. Run generateAllPetCombinations() first to fix this.`);
    }

    logger.write("Amount of PETs Generated: " + state.petList.length);

    if (state.petList.length <= 1000) {
        for (var i = 0; i < state.petList.length; i++) {
            logger.write("PET" + i + "(" + state.petList[i] + ")");
        }
    } else {
        // You can try state.petList.length but it will take a while to load all so I put 1000 just for display purposes.
        for (var i = 0; i < 1000; i++) {
            logger.write("PET" + i + "(" + state.petList[i] + ")");
        }
    }
}

//This is just used for testing (not actually used in algorithm)
function getDistance(object_x, object_y) {
    //let max_distance = 10;
    let distance = 10;
    if (object_x.Colour == object_y.Colour) {
        distance--;
    }
    if (object_x.Type == object_y.Type) {
        distance--;
    }
    if (object_x.Name == object_y.Name) {
        distance--;
    }
    if (object_x.Diet == object_y.Diet) {
        distance--;
    }
    if (object_x.OwnerName == object_y.OwnerName) {
        distance--;
    }
    if ((object_x.NumberOfLegs < 4 && object_y.NumberOfLegs < 4) || (object_x.NumberOfLegs == 4 && object_y.NumberOfLegs == 4) || (object_x.NumberOfLegs > 4 && object_y.NumberOfLegs > 4)) {
        distance--;
    }
    if ((object_x.Age < 10 && object_y.Age < 10) || (object_x.Age == 10 && object_y.Age == 10) || (object_x.Age > 10 && object_y.Age > 10)) {
        distance--;
    }
    if ((object_x.NumberOfEyes < 2 && object_y.NumberOfEyes < 2) || (object_x.NumberOfEyes == 2 && object_y.NumberOfEyes == 2) || (object_x.NumberOfEyes > 2 && object_y.NumberOfEyes > 2)) {
        distance--;
    }
    if ((object_x.Height < 40 && object_y.Height < 40) || (object_x.Height == 40 && object_y.Height == 40) || (object_x.Height > 40 && object_y.Height > 40)) {
        distance--;
    }
    if ((object_x.Weight < 1000 && object_y.Weight < 1000) || (object_x.Weight == 1000 && object_y.Weight == 1000) || (object_x.Weight > 1000 && object_y.Weight > 1000)) {
        distance--;
    }
    return distance;
}

//The maximum and the minimum are inclusive
function getRandomInt(min, max) {
    if (!Number.isInteger(min) || !Number.isInteger(max) || min < 0 || max < 0) {
        console.error(`getRandomInt(number: min, number: max) requires two positive integers. min=${min} and max=${max} are not valid arguments.`);
        return null;
    }
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

/*//Generates a random Pet object. This will most likley be used with a Oracle. Not from an object heap.
function getRandomPet() {
    return {
        Colour: attributePool.color[getRandomInt(0, 2)],
        Type: attributePool.type[getRandomInt(0, 2)],
        Name: attributePool.name[getRandomInt(0, 2)],
        Diet: attributePool.diet[getRandomInt(0, 2)],
        OwnerName: attributePool.owner[getRandomInt(0, 2)],
        NumberOfLegs: getRandomInt(0, 8), // <4, 4, >4          range(0-8) inclusive          
        Age: getRandomInt(0, 20), //<10, 10, >10        range(0-20)
        NumberOfEyes: getRandomInt(0, 4), //<2, 2, >2       range(0-4)
        Height: getRandomInt(0, 80), //<40, 40, >40                   range(0-80)
        Weight: getRandomInt(0, 2000) //<1000, 1000, >1000                range(0-2000)
    };
}*/

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

function generateCandiates() {
    if (state.petList.length === 0) {
        console.error(`Cannot generate candidates. There is currently no data in the state.petList array. Run generateAllPetCombinations() first to fix this.`);
        return null;
    }
    for (let i = 0; i < 10; i++) {
        state.candidates[i] = state.petList[getRandomInt(0, state.petList.length - 1)];
    }
}

//This creates S set for a candidate (see Article 2 for knowledge on S set)
function returnObjectChoicePosition(pet) {
    var categoriesAndChoices = [];

    var i;
    for (i = 0; i < 40; i++) categoriesAndChoices[i] = 0;
    var colour_index = attributePool.color.indexOf(pet.Colour);
    categoriesAndChoices[0 + (colour_index + 1)] = 1;

    var type_index = attributePool.type.indexOf(pet.Type);
    categoriesAndChoices[4 + (type_index + 1)] = 1;

    var name_index = attributePool.name.indexOf(pet.Name);
    categoriesAndChoices[8 + (name_index + 1)] = 1;

    var diet_index = attributePool.diet.indexOf(pet.Diet);
    categoriesAndChoices[12 + (diet_index + 1)] = 1;

    var owner_name_index = attributePool.owner.indexOf(pet.OwnerName);
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


function generateAllPetCombinations() {
    for (let typesIndex = 0; typesIndex < attributePool.type.length; typesIndex++) {
        for (let colorsIndex = 0; colorsIndex < attributePool.color.length; colorsIndex++) {
            for (let namesIndex = 0; namesIndex < attributePool.name.length; namesIndex++) {
                for (let dietsIndex = 0; dietsIndex < attributePool.diet.length; dietsIndex++) {
                    for (let ownersNameIndex = 0; ownersNameIndex < attributePool.owner.length; ownersNameIndex++) {
                        for (let numOfLegsIndex = 0; numOfLegsIndex < attributePool.numLegs.length; numOfLegsIndex++) {
                            for (let ageIndex = 0; ageIndex < attributePool.age.length; ageIndex++) {
                                for (let numOfEyesIndex = 0; numOfEyesIndex < attributePool.numEyes.length; numOfEyesIndex++) {
                                    for (let heightIndex = 0; heightIndex < attributePool.height.length; heightIndex++) {
                                        for (let weightIndex = 0; weightIndex < attributePool.weight.length; weightIndex++) {
                                            state.petList.push({
                                                type: attributePool.type[typesIndex],
                                                color: attributePool.color[colorsIndex],
                                                name: attributePool.name[namesIndex],
                                                diet: attributePool.diet[dietsIndex],
                                                owner: attributePool.owner[ownersNameIndex],
                                                numLegs: attributePool.numLegs[numOfLegsIndex],
                                                age: attributePool.age[ageIndex],
                                                numEyes: attributePool.numEyes[numOfEyesIndex],
                                                height: attributePool.height[heightIndex],
                                                weight: attributePool.weight[weightIndex]
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
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

function ART() {
    var S = [];
    var k;
    for (k = 0; k < 40; k++) S[k] = 0; //init S set

    var E = []; //past test cases
    var test_case; //is initialized by either the first test case or the candidate with the largest distance

    var n = 0;

    var g; //for loop needs to be replaced with while(condition- failed test case)
    for (g = 0; g < 60000; g++) {
        n++; //dont ask me why n is incremented here rather than at the end of the loop... the paper told me to do it. 

        if (n === 1) {
            test_case = state.petList[getRandomInt(0, state.petList.length - 1)];
        } else {
            generateCandiates(); //generate state.candidates

            var max_sum = -1;
            var current_sum;
            var candidate;
            var candidate_S;
            var max_candidate_index = -1;

            var j;
            for (j = 0; j < state.candidates.length; j++) { //calculating the candidate with teh max distance between it and the S set
                candidate = state.candidates[j];
                candidate_S = returnObjectChoicePosition(candidate);
                current_sum = calculate_sum_distance(n - 1, S, candidate_S);

                if (current_sum > max_sum) {
                    max_sum = current_sum;
                    max_candidate_index = j;
                }
            }
            test_case = state.candidates[max_candidate_index];
        }

        E[n - 1] = test_case;

        var i; //incrementing S set accordingly
        var S_test_case = returnObjectChoicePosition(test_case);
        for (i = 0; i < S.length; i++)
            if (S_test_case[i] == 1) S[i]++;
    }
}

logger.write("done");

//generateAllPetCombinations();
displayPETsGenerated();


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

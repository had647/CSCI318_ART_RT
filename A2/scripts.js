// Just using the same values made at the top of the page.
const attributePool = {
    type: ["Cat", "Dog", "Bird"],
    color: ["Blue", "Red", "Green"],
    name: ["James", "Tim", "Bob"],
    diet: ["Rats", "Birdseed", "Apples"],
    owner: ["Jim", "Jane", "Bryce"],
    numLegs: ["<4", "4", ">4"],
    age: ["<10", "10", ">10"],
    numEyes: ["<2", "2", ">2"],
    height: ["<40", "40", ">40"],	// in centimeters?
    weight: ["<1000", "1000", ">1000"]  // in grams?
};

let state = {
    candidates: [],
    petList: [],
    errorPct: 0.01,
    initialized: false,
    numTests: 1000
};

let logger = {
    write: function (input) {
        document.getElementById("console-output").value += `${input}\n\n`;
    },
    error: function (input) {
        document.getElementById("console-output").value += `ERROR: ${input}\n\n`;
    },
    clear: function () {
        document.getElementById("console-output").value = "";
    }
};

function displayPETsGenerated() {
    if (state.petList.length === 0) {
        console.warn(`There is currently no data in the state.petList array. Run generateAllPetCombinations() first to fix this.`);
    }

    logger.write(`Number of PETs generated: ${state.petList.length}`);
    for (let i = 0; i < ((state.petList.length > 1000) ? 1000 : state.petList.length); i++) {
        logger.write(`PET Index[${i}]:(${state.petList[i].type},${state.petList[i].color},${state.petList[i].name},${state.petList[i].diet},${state.petList[i].owner},${state.petList[i].numLegs},${state.petList[i].age},${state.petList[i].numEyes},${state.petList[i].height},${state.petList[i].weight})`);
    }
}

/*This is how the distance is calculated between the two objects*/
/*Need to work out how to compensate for the circumstance that one object might not have the same properties as another*/
/*e.g. If Pet_0 has a property 'Name', but Pet_1 does not. Then the distance btween them woudl be increased by 1*/
function getDistance(object_x, object_y) {
    let distance = 10;
    for (let key in object_x) {
        if (object_x[key] === object_y[key])
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
    let i, categoriesAndChoices = [];

    for (i = 0; i < 40; i++) categoriesAndChoices[i] = 0;
    let colour_index = attributePool.color.indexOf(pet.color);
    categoriesAndChoices[0 + (colour_index + 1)] = 1;

    let type_index = attributePool.type.indexOf(pet.type);
    categoriesAndChoices[4 + (type_index + 1)] = 1;

    let name_index = attributePool.name.indexOf(pet.name);
    categoriesAndChoices[8 + (name_index + 1)] = 1;

    let diet_index = attributePool.diet.indexOf(pet.diet);
    categoriesAndChoices[12 + (diet_index + 1)] = 1;

    let owner_name_index = attributePool.owner.indexOf(pet.owner);
    categoriesAndChoices[16 + (owner_name_index + 1)] = 1;

    let number_of_legs_index;
    if (pet.numLegs === "<4") number_of_legs_index = 0;
    else if (pet.numLegs === "4") number_of_legs_index = 1;
    else if (pet.numLegs === ">4") number_of_legs_index = 2;
    categoriesAndChoices[20 + (number_of_legs_index + 1)] = 1;

    let age_index;
    if (pet.age === "<10") age_index = 0;
    else if (pet.age === "10") age_index = 1;
    else if (pet.age === ">10") age_index = 2;
    categoriesAndChoices[24 + (age_index + 1)] = 1;

    let number_of_eyes_index;
    if (pet.numEyes === "<2") number_of_eyes_index = 0;
    else if (pet.numEyes === "2") number_of_eyes_index = 1;
    else if (pet.numEyes === ">2") number_of_eyes_index = 2;
    categoriesAndChoices[28 + (number_of_eyes_index + 1)] = 1;

    let height_index;
    if (pet.height === "<40") height_index = 0;
    else if (pet.height === "40") height_index = 1;
    else if (pet.height === ">40") height_index = 2;
    categoriesAndChoices[32 + (height_index + 1)] = 1;

    let weight_index;
    if (pet.weight === "<1000") weight_index = 0;
    else if (pet.weight === "1000") weight_index = 1;
    else if (pet.weight === ">1000") weight_index = 2;
    categoriesAndChoices[36 + (weight_index + 1)] = 1;

    return categoriesAndChoices;
}

//Calculates the distance between the candidate S set and the 'main' S set
function calculate_sum_distance(numTestCases, S_array, candidate_S) {
    let i, choice_1, choice_2, choice_3, choice_4, choice_5, choice_6, choice_7, choice_8, choice_9, choice_10;

    for (i = 0; i < 4; i++)
        if (candidate_S[i] == 1) choice_1 = S_array[i];
    for (i = 4; i < 8; i++)
        if (candidate_S[i] == 1) choice_2 = S_array[i];
    for (i = 8; i < 12; i++)
        if (candidate_S[i] == 1) choice_3 = S_array[i];
    for (i = 12; i < 16; i++)
        if (candidate_S[i] == 1) choice_4 = S_array[i];
    for (i = 16; i < 20; i++)
        if (candidate_S[i] == 1) choice_5 = S_array[i];
    for (i = 20; i < 24; i++)
        if (candidate_S[i] == 1) choice_6 = S_array[i];
    for (i = 24; i < 28; i++)
        if (candidate_S[i] == 1) choice_7 = S_array[i];
    for (i = 28; i < 32; i++)
        if (candidate_S[i] == 1) choice_8 = S_array[i];
    for (i = 32; i < 36; i++)
        if (candidate_S[i] == 1) choice_9 = S_array[i];
    for (i = 36; i < 40; i++)
        if (candidate_S[i] == 1) choice_10 = S_array[i];

    return (numTestCases - choice_1) + (numTestCases - choice_2) + (numTestCases - choice_3) + (numTestCases - choice_4) + (numTestCases - choice_5) +
        (numTestCases - choice_6) + (numTestCases - choice_7) + (numTestCases - choice_8) + (numTestCases - choice_9) + (numTestCases - choice_10);
}

function generateErrorRegion() {
    let numErrItems = Math.floor(state.errorPct * state.petList.length);
    logger.write(`Generating error region: ${state.errorPct} x ${state.petList.length} = ${numErrItems}`);
    let startIdx = getRandomInt(0, state.petList.length - 1);
    state.petList[startIdx].error = true;

    for (let diffThreshold = 0; diffThreshold < 11; diffThreshold++) {
        for (let petIdx = 0; petIdx < state.petList.length; petIdx++) {
            if (getDistance(state.petList[startIdx], state.petList[petIdx]) <= diffThreshold) {
                state.petList[petIdx].error = true;
                numErrItems--;
            }
            if (numErrItems === 0) {
                return;
            }
        }
    }
}

function generateAllPetCombinations() {
    if (!state.initialized) {
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
                                                    weight: attributePool.weight[weightIndex],
                                                    error: false
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
        state.initialized = true;
    }
}

function ART() {
    let S = [];
    let k;
    for (k = 0; k < 40; k++) {
        S[k] = 0; //init S set
    }

    let E = []; //past test cases
    let test_case; //is initialized by either the first test case or the candidate with the largest distance
    let breakLoop = false;
    let n = 0;

    //let g; //for loop needs to be replaced with while(condition- failed test case)
    while (breakLoop === false) {
        n++; //dont ask me why n is incremented here rather than at the end of the loop... the paper told me to do it. 

        if (n === 1) {
            test_case = state.petList[getRandomInt(0, state.petList.length - 1)];
        } else {
            generateCandiates(); //generate state.candidate

            let max_sum = -1;
            let current_sum;
            let candidate;
            let candidate_S;
            let max_candidate_index = -1;

            let j;
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
            if (test_case.error === true) {
                breakLoop = true;
            }
        }

        E[n - 1] = test_case;

        let i; //incrementing S set accordingly
        let S_test_case = returnObjectChoicePosition(test_case);
        for (i = 0; i < S.length; i++) {
            if (S_test_case[i] == 1) {
                S[i]++;
            }
        }
    }
    //console.log(`ART n: ${n}`);
    //logger.write(`n: ${n}`);
    return n;
}

function RT() {
    let numberOfCorrectPets = 0;
    while (!state.petList[getRandomInt(0, state.petList.length - 1)].error) { // If the pet is having error: false
        numberOfCorrectPets++;
    }
    return numberOfCorrectPets;
}

function RUN() {
    if (state.initialized != true) {
        logger.error(`Please initalize the data set first.`);
    } else {
        logger.write(`Running ${state.numTests} tests...`);
        let ARTwins = 0, RTwins = 0, ties = 0, artTemp, rtTemp;

        for (let i = 0; i < state.numTests; i++) {
            artTemp = ART();
            rtTemp = RT();
            if (artTemp > rtTemp) {
                RTwins++;
            } else if (artTemp < rtTemp) {
                ARTwins++;
            } else {
                ties++;
            }
        }
        logger.write(`ARTwins: ${ARTwins}\nRTwins: ${RTwins}\nTies: ${ties}`);
    }
}


function initialize() {
    let userErrorPercentInput = parseInt(document.getElementById("userErrorPrecent").value);

    if (userErrorPercentInput < 100 && userErrorPercentInput > 0) {
        state.errorPct = userErrorPercentInput / 100; //convert to percentage
        logger.write(`Error Region Percent set to ${userErrorPercentInput}%...`);
    } else {
        logger.write(`Default Error Region Percent set to 1%...`);
    }

    generateAllPetCombinations();
    logger.write(`Generation of all PET combinations complete...`);
    generateErrorRegion();
    logger.write(`Generation of error region complete...`);
    logger.write(`Initialization process now complete.`);
}

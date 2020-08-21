let state = {
  testCases: [],
  candidates: [],
  failRegion: {
    coords: { 'x': null, 'y': null },
    edgeSize: null,
    area: null
  },
  canvasSize: null,
  canvasRef: null,
  collision: false,
  loopCount: 0
};

//calculates the smallest distance between a candidate case, and all current test cases
function calculate_min_distance(candidate) {

  let min_distance = -1;  //smallest distance after measuring distance between candidate and all other test cases
  let di = -1;            //distance between the candidate and all test cases

  for (let i = 0; i < state.testCases.length; i++) {     //iterating through each test case

    di = Math.sqrt(Math.pow((candidate.x - state.testCases[i].x), 2) + Math.pow((candidate.y - state.testCases[i].y), 2));   //equation for measuring distance

    if (i === 0) { min_distance = di; }             //assume the first distance is the smallest
    if (di < min_distance) { min_distance = di; }   //if a smaller distance is found, update the min_distance
  }
  //console.log(min_distance);
  return min_distance;
}

function getNextTestCase() {
  state.loopCount++;
  //let k = 1; //should be input argument
  let d = 0;
  genNewCandidates();
  //console.log(state.candidates);
  let t;
  let di;
  for (let i = 0; i < state.candidates.length; i++) {
    di = calculate_min_distance(state.candidates[i]);
    //console.log(di);
    if (di > d) {
      d = di;
      t = state.candidates[i];
    }
  }
  state.testCases.push(t);
  checkForCollision();
  
  document.getElementById("artCounter").innerHTML = "Test case: " + state.loopCount;
}

function genNewCandidates() {
  for (let i = 0; i < 10; i++) {     //randomly generate new set of candidates
    state.candidates[i] = { 'x': Math.random() * state.canvasSize, 'y': Math.random() * state.canvasSize };
  }
}

function initBlankCanvas() {
  if (state.canvasSize === null) {
    console.error("Need to initialize state.canvasSize before trying to initialize the canvas!");
    return;
  }

  //this is probably slow - needs optimizing - altered for readability
  //sets height and width based on edge_size
  document.getElementById("artContainer").innerHTML = `
    <h2>ART</h2>
    <canvas id="myCanvas" width="${state.canvasSize}" height="${state.canvasSize}"></canvas>
    <h3 id="artOutput" class="output"></h3>
	<h5 id="artCounter" class="output"></h5>`;
  document.getElementById("rtContainer").innerHTML = `
    <h2>RT</h2>
    <canvas id="myCanvas" width="${state.canvasSize}" height="${state.canvasSize}"></canvas>
    <h3 id="rtOutput" class="output"></h3>
	<h5 id="rtCounter" class="output"></h5>`;
}

function initState() {
  //resetstate();
  state.testCases = [];
  state.collision = false;
  state.loopCount = 0;

  let fail_region_percent = parseFloat(document.getElementById("failPct").value) / 100;  //user will change this
  console.log(`Using failure pct: ${fail_region_percent}`);

  let edge_size = parseInt(document.getElementById("edgeSize").value);  //change this to set the HxW of the canvas
  console.log(`Using edge size: ${edge_size}px`);

  if (isNaN(fail_region_percent) || fail_region_percent > 1 || fail_region_percent <= 0 || isNaN(edge_size) || edge_size <= 0) {
    console.error("Enter valid failure percentage: 1%-100% and canvas edge size: 250px-800px!");
    return;
  } else {
    console.log("INPUT VALID");
  }

  let fail_region_edge_size = Math.sqrt(edge_size * edge_size * fail_region_percent);  //width and height of fail region

  let border_limit_max = edge_size - fail_region_edge_size; //determines the maximum x and y coordinate for the fail region to prevent the fail region from being cut off by the border

  let random_x = Math.random() * border_limit_max;  //generates a random number inclusively between 0 and the border_limit_max
  let random_y = Math.random() * border_limit_max;  //" "

  let random_x_test_case = Math.random() * edge_size; //these may need to change
  let random_y_test_case = Math.random() * edge_size;

  //update app state
  state.failRegion.edgeSize = fail_region_edge_size;
  state.failRegion.coords.x = random_x;
  state.failRegion.coords.y = random_y;
  state.failRegion.area = fail_region_percent;
  state.canvasSize = edge_size;
  state.testCases.push({ 'x': random_x_test_case, 'y': random_y_test_case });
  initBlankCanvas();
  state.canvasRef = document.getElementById("myCanvas");     //setting up canvas stuff
  drawErrorRegion("green");
  //document.getElementById("output_result").innerHTML = "MISSED!"; //default??? - removed: set by collision
  checkForCollision();
}

function checkForCollision() {
  //collision demonstration
  let i = state.testCases.length - 1; //'i' is the test case index to check for collisions with
  if (state.testCases[i].x >= state.failRegion.coords.x && state.testCases[i].x <= (state.failRegion.coords.x + state.failRegion.edgeSize)
    && state.testCases[i].y >= state.failRegion.coords.y && state.testCases[i].y <= (state.failRegion.coords.y + state.failRegion.edgeSize)) {
    console.log("COLLISION");
    document.getElementById("artOutput").innerHTML = `HIT!✔️`;
    state.collision = true;
    drawErrorRegion("red");
    //stop checking for collisions
  } else { //no collision occurs
    //drawErrorRegion("green");
    document.getElementById("artOutput").innerHTML = `MISSED!❌`;
  }
  drawLastTestCase(); //this gets drawn last over the top of whatever else is there
}

function drawErrorRegion(color) {
  let ctx = state.canvasRef.getContext("2d");                    //setting up the fail region
  ctx.fillStyle = color;                            //colour of fail region
  ctx.fillRect(state.failRegion.coords.x, state.failRegion.coords.y, state.failRegion.edgeSize, state.failRegion.edgeSize);//rendering the fail region
}

function drawLastTestCase() {
  let ctx = state.canvasRef.getContext("2d");
  //drawing a square is more performant than drawing a circle
  ctx.fillStyle = "black";                            //colour of fail region
  ctx.fillRect(state.testCases[state.testCases.length - 1].x, state.testCases[state.testCases.length - 1].y, 2, 2); //rendering the test case
}

function driver() {
  initState();
  if (state.failRegion.area < 0.001) {
    while (state.collision === false) {
      getNextTestCase();
    }
  } else {
    var loop = setInterval(function () {
      if (state.collision === true) {
        clearInterval(loop);
      } else {
        getNextTestCase();
      }
    }, 1);
  }
  
}
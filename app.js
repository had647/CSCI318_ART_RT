let state = {
  testCases: [],
  lastRT: null,
  candidates: [],
  failRegion: {
    coords: { 'x': null, 'y': null },
    edgeSize: null,
    area: null
  },
  canvasSize: null,
  canvasRefART: null,
  canvasRefRT: null,
  collisionART: false,
  collisionRT: false,
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

function getNextARTTestCase() {
  //state.loopCount++;
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
  checkForCollisionART();

}

function genNewCandidates() {
  for (let i = 0; i < 10; i++) {     //randomly generate new set of candidates
    state.candidates[i] = genRandomPoint();
  }
}

function genRandomPoint() {
	return { 'x': Math.random() * state.canvasSize, 'y': Math.random() * state.canvasSize };
}

function checkForCollisionRT() {

  let point = genRandomPoint();
  state.lastRT = point;
  if (point.x >= state.failRegion.coords.x && point.x <= (state.failRegion.coords.x + state.failRegion.edgeSize)
    && point.y >= state.failRegion.coords.y && point.y <= (state.failRegion.coords.y + state.failRegion.edgeSize)) {
    console.log("collisionRT");
    document.getElementById("rtOutput").innerHTML = `HIT!✔️`;
    state.collisionRT = true;
    drawErrorRegion("red");
    
    //stop checking for collisions
    // implement when checkbox is ticked..
  } //else { //no collisionART occurs
    //drawErrorRegion("green");
    //document.getElementById("rtOutput").innerHTML = `MISSED!❌`;
  //}
  drawLastTestCase(2);
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
    <canvas id="myCanvasART" width="${state.canvasSize}" height="${state.canvasSize}"></canvas>
    <h3 id="artOutput" class="output"></h3>
	<h5 id="artCounter" class="output"></h5>`;
  document.getElementById("rtContainer").innerHTML = `
    <h2>RT</h2>
    <canvas id="myCanvasRT" width="${state.canvasSize}" height="${state.canvasSize}"></canvas>
    <h3 id="rtOutput" class="output"></h3>
	<h5 id="rtCounter" class="output"></h5>`;
}

function initState() {
  //resetstate();
  state.testCases = [];
  state.collisionART = false;
  state.collisionRT = false;
  state.loopCount = 0;
  state.lastRT = null;

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
  state.canvasRefART = document.getElementById("myCanvasART");     //setting up canvas stuff
  state.canvasRefRT = document.getElementById("myCanvasRT");
  drawErrorRegion("green");
  //document.getElementById("output_result").innerHTML = "MISSED!"; //default??? - removed: set by collisionART
  checkForCollisionART();
  state.lastRT = genRandomPoint();
  drawLastTestCase(2);
}

function checkForCollisionART() {
  //collisionART demonstration
  let i = state.testCases.length - 1; //'i' is the test case index to check for collisions with
  if (state.testCases[i].x >= state.failRegion.coords.x && state.testCases[i].x <= (state.failRegion.coords.x + state.failRegion.edgeSize)
    && state.testCases[i].y >= state.failRegion.coords.y && state.testCases[i].y <= (state.failRegion.coords.y + state.failRegion.edgeSize)) {
    console.log("collisionART");
    document.getElementById("artOutput").innerHTML = `HIT!✔️`;
    state.collisionART = true;
    drawErrorRegion("red");
    //stop checking for collisions
  } //else { //no collisionART occurs
    //drawErrorRegion("green");
    //document.getElementById("artOutput").innerHTML = `MISSED!❌`;
  //}
  drawLastTestCase(1); //this gets drawn last over the top of whatever else is there
  
}
// Needs to be BADLY refactored coz now it is aids
function drawErrorRegion(color) {
  if (color === 'green') { 
    let ctxART = state.canvasRefART.getContext("2d");                    //setting up the fail region
    let ctxRT = state.canvasRefRT.getContext("2d");      
    
    ctxART.fillStyle = color;                            //colour of fail region
    ctxART.fillRect(state.failRegion.coords.x, state.failRegion.coords.y, state.failRegion.edgeSize, state.failRegion.edgeSize);//rendering the fail region
    
    ctxRT.fillStyle = color;                            //colour of fail region
    ctxRT.fillRect(state.failRegion.coords.x, state.failRegion.coords.y, state.failRegion.edgeSize, state.failRegion.edgeSize);//rendering the fail region
  } else {
    if (state.collisionART === true) {
      let ctxART = state.canvasRefART.getContext("2d");
      ctxART.fillStyle = color;                            //colour of fail region
      ctxART.fillRect(state.failRegion.coords.x, state.failRegion.coords.y, state.failRegion.edgeSize, state.failRegion.edgeSize);//rendering the fail region
    }
    if (state.collisionRT === true) {
      let ctxRT = state.canvasRefRT.getContext("2d");
      ctxRT.fillStyle = color;                            //colour of fail region
      ctxRT.fillRect(state.failRegion.coords.x, state.failRegion.coords.y, state.failRegion.edgeSize, state.failRegion.edgeSize);//rendering the fail region
    }
  }
}

function drawLastTestCase(canvas) {
  let ctx;
  if (canvas === 1) {
    ctx = state.canvasRefART.getContext("2d");
    //drawing a square is more performant than drawing a circle
    ctx.fillStyle = "black";                            //colour of fail region
    ctx.fillRect(state.testCases[state.testCases.length - 1].x, state.testCases[state.testCases.length - 1].y, 2, 2); //rendering the test case
  } else if (canvas === 2) {
    ctx = state.canvasRefRT.getContext("2d");
    //drawing a square is more performant than drawing a circle
    ctx.fillStyle = "black";                            //colour of fail region
    ctx.fillRect(state.lastRT.x, state.lastRT.y, 2, 2); //rendering the test case
  } else {return;}
  
  
}

function driver() {
  initState();
  if (state.failRegion.area < 0.001) {
    while (state.collisionART === false && state.collisionRT === false) {
      state.loopCount++;
      getNextARTTestCase();
      checkForCollisionRT();
    }
    document.getElementById("artCounter").innerHTML = "Test case: " + state.loopCount;
  } else {
    var loop = setInterval(function () {
      if (state.collisionART === true || state.collisionRT === true) {
        clearInterval(loop);
      } else {
        state.loopCount++;
        document.getElementById("artCounter").innerHTML = "Test case: " + state.loopCount;
        getNextARTTestCase();
        checkForCollisionRT();
      }
    }, 1);
  }
  
}
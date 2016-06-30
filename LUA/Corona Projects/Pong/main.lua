-----------------------------------------------------------------------------------------
--
-- main.lua
--
-----------------------------------------------------------------------------------------

-- Your code here
local physics = require("physics")
physics.start()

physics.setGravity(0,0)

local rand = (math.random(-3, 3))
if (rand == 0) then
	rand = rand + 1
end

local score = 0
local lives = 3

local pause = 0

local outOfBounds = display.newRect(384, 1100, 768, 20)
physics.addBody(outOfBounds, "static")

local background = display.newImage("background.png")
background.x = 384; background.y = 512

local boundary1 = display.newImage("Boundary.png")
boundary1.x = 10; boundary1.y = 512
physics.addBody(boundary1, "static", {friction = 0, bounce = 0})

local boundary2 = display.newImage("Boundary.png")
boundary2.x = 758; boundary2.y = 512
physics.addBody(boundary2, "static", {friction = 0, bounce = 0})

local boundary3 = display.newImage("Boundary3.png")
boundary3.x = 384; boundary3.y = 10
physics.addBody(boundary3, "static", {friction = 0, bounce = 0})

local paddle = display.newImage("Paddle.png")
paddle.x = 384; paddle.y = 1000
physics.addBody(paddle, "static", {friction = -1, bounce = 0})
paddle.myName = "Paddle"

local ball = display.newCircle(384, 200, 20)
ball:setFillColor(255,0,0)
physics.addBody(ball, {friction = 0, bounce = 1})
ball:applyForce(rand, 1, 384, 200)
ball:setLinearVelocity(0, 700)

local scoreText = display.newText("Score: " .. score, 120, 50, native.systemFont, 30 )
scoreText:setFillColor(1, 0, 0)

local livesText = display.newText("Lives: " .. lives, 628, 50, native.systemFont, 30 )
livesText:setFillColor(1, 0, 0)

local function onKeyEvent(event)
    local message = "Key '" .. event.keyName .. "' was pressed " .. event.phase
	if ( event.keyName == "right" and paddle.x ~= 704 and pause == 0) then
		paddle.x = paddle.x + 20
	end
	if ( event.keyName == "left" and paddle.x ~= 64 and pause == 0) then
		paddle.x = paddle.x - 20
	end
    print(message .. " and the x position of the paddle is " .. paddle.x)
end
Runtime:addEventListener("key", onKeyEvent)


local function onLocalCollision(self, event)
    if (event.phase == "began") then
        print(self.myName .. " collision")
		score = score + 5
		scoreText.text = "Score: " .. score
    end
end

paddle.collision = onLocalCollision
paddle:addEventListener("collision", paddle)

local function listener(event)
    ball.x = 384; ball.y = 200
	ball:applyForce(rand, 1, 384, 200)
	ball:setLinearVelocity(0, 700)
	print("test")
end

local function onOutOfBounds(self, event)
    if (event.phase == "began") then
        lives = lives - 1
		livesText.text = "Lives: " .. lives
		if (lives == 0) then
			local gameOver = display.newText("Game Over", 384, 500, native.systemFont, 50 )
			gameOver:setFillColor( 1, 0, 0 )
			physics.pause()
			pause = pause + 1
		else
			timer.performWithDelay(10, listener)
		end
    end
end

outOfBounds.collision = onOutOfBounds
outOfBounds:addEventListener("collision", outOfBounds)
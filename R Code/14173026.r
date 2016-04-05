midscores = read.csv("midscores.csv", header=T)
set.seed(14173026)
midhigh = sample(midscores$high, size=20)
midlow = sample(midscores$low , size=20)

hist(midhigh)$counts
hist(midlow)$counts
boxplot(midhigh, horizontal=T)
boxplot(midlow, horizontal=T)

sd(midhigh)
sd(midlow)
summary(midhigh)
summary(midlow)
IQR(midhigh)
IQR(midlow)
qqnorm(midhigh); qqline(midhigh)
qqnorm(midlow); qqline(midlow)

t.test(c(midhigh, midlow), mu=7.5)
var.test(midhigh, midlow)
t.test(midhigh, midlow, var.equal=TRUE)


simreps = 1000
xbar = rep(0, simreps)
for(i in 1:simreps)
{
  xbar[i] = head(rbinom(n=50, size=1, prob=0.5))
}
hist(xbar, xlim=c(0,1))
qqnorm(xbar, ylim=c(0,1));qqline(xbar)

for(i in 1:simreps)
{
  xbar[i] = head(rbinom(n=50, size=1, prob=0.05))
}
hist(xbar, xlim=c(0,1))
qqnorm(xbar, ylim=c(0,1));qqline(xbar)


pbinom(5,size=10,prob=0.65,lower=F)
sum(dbinom(0:29,size=100,prob=0.2))
sum(dbinom(15:30,size=50,prob=0.32))

dpois(8,lambda=6)
ppois(35,lambda=41,lower=F)
sum(dpois(2:5,lambda=1))

pnorm(12,mean=7,sd=2.5,lower=F)
pnorm(9.8,mean=10,sd=1,lower=F)
sum(pnorm(0:37,mean=50,sd=5))
sum(pnorm(5:7,mean=5,sd=3.6))

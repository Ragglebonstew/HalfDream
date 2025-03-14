This repository is no longer in use. Go to [Faesied](https://github.com/Ragglebonstew/Faesied) for the current one. Mod is still called Half Dream, some stuff just happened on my end.

# HalfDream

Changelog

0.5.0
+ added dream chunk api for infinite layers!
- fixed bug with client side chunk sync packet
- found some god-awful bug with getOpacity in abstract block mixin
+ added the ability for blocks to become dream, similar to old dream blocks
+ added cbsi item for debug. Makes a ding sound when a block is either dream or disturbed
+ added dream powder item which makes a block dream (at least until I can fix the block mixin bug)
- fixed bug where dream blocks replaced improperly with already replaceable blocks
- fixed bug where replacing replaceable block with dream block crashed the game
- optimized chunk sync packets
- fixed bug where client side entities all used sequence dream state
- fixed bug where entities were suffocating in dream and disturbed blocks
- fixed bug where in-house dream blocks didn't default to dream blocks
+ made in-house dream blocks copy dream state of player if applicable
- fixed in-house dream block settings
- chunk sync optimization

0.4.3
+ added texture for dream resin
- fixed bug where resin spawned off center of disturbed block
- fixed bug where other players would ignore player state
- fixed bug where sodium ignored dream light levels

0.4.2
- fixed dream entity component to use entity.class instead of livingentity.class to resolve item problems
- fixed all the nonsense resulting from previous fix
- fixed bug where items would pickup in wrong state
- fixed bug where dream resin dropped in real world, not dream world
- fixed bug where client would ignore sequence state
+ added /setdream command to toggle dream state for any entity
- fixed bug where items would ignore player state when dropped
- fixed bug where /setdream command didn't require state value
- deprecated all isDream checks
- fixed bug where dream blocks didn't render in dual state
- fixed bug where disturbed blocks didn't de-render in dual state


0.4.1
+ Added compatibility with Sodium rendering by adding WorldSlice mixin
- fixed dream block collision
+ began fiddling with item groups
+ added isDream check for items
- fixed abstract block collision that ignored items

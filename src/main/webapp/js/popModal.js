/* popModal - 23.05.14 */
/* popModal */
(function($) {
	$.fn.popModal = function(method) {
		var elem = $(this),
		elemObj,
		isFixed = '',
		expandView = true,
		closeBut = '',
		elemClass = 'popModal',
		_options,
		animTime,
		effectIn = 'fadeIn',
		effectOut = 'fadeOut',
		bl = 'bottomLeft',
		bc = 'bottomCenter',
		br = 'bottomRight',
		lt = 'leftTop',
		lc = 'leftCenter',
		rt = 'rightTop',
		rc = 'rightCenter';
	
		var methods = {
			init : function(params) {
				var _defaults = {
					html: '',
					placement: bl,
					showCloseBut: true,
					onDocumentClickClose : true,
					onOkBut: function() {return true;},
					onCancelBut: function() {},
					onLoad: function() {},
					onClose: function() {}
				};
				_options = $.extend(_defaults, params);
				
				if (elem.next('div').hasClass(elemClass)) {
					popModalClose();
				} else {
					$('html.' + elemClass + 'Open').off('.' + elemClass + 'Event').removeClass(elemClass + 'Open');
					$('.' + elemClass + '_source').replaceWith($('.' + elemClass + '_content').children());
					$('.' + elemClass).remove();

					if (_options.showCloseBut) {
						closeBut = $('<button type="button" class="close">&times;</button>');
					}
					if (elem.css('position') == 'fixed') {
						isFixed = 'position:fixed;';
					}
					var tooltipContainer = $('<div class="' + elemClass + ' animated" style="' + isFixed + '"></div>');
					var tooltipContent = $('<div class="' + elemClass + '_content ' + elemClass + '_contentOverflow"></div>');
					tooltipContainer.append(closeBut, tooltipContent);
					
					if ($.isFunction(_options.html)) {
						var beforeLoadingContent = 'Please, waiting...';
						tooltipContent.append(beforeLoadingContent);
						_options.html(function(loadedContent) {
							tooltipContent.empty().append(loadedContent);
							elemObj = $('.' + elemClass);
							expandView = true;
							if (tooltipContent[0].innerHTML.search(/<form/) != -1) {
								elemObj.find('.' + elemClass + '_content').removeClass(elemClass + '_contentOverflow');
							} else {
								elemObj.find('.' + elemClass + '_content').addClass(elemClass + '_contentOverflow');
							}
							getPlacement();
						});
					} else {
						if ($.type(_options.html) == 'object') {
							_options.html.after($('<div class="popModal_source"></div>'));
						}
						tooltipContent.append(_options.html);
					}
					elem.after(tooltipContainer);

					elemObj = $('.' + elemClass);
					if (elemObj.find('.' + elemClass + '_footer')) {
						elemObj.find('.' + elemClass + '_content').css({marginBottom: elemObj.find('.' + elemClass + '_footer').outerHeight() + 'px'});
					}
					
					if (!$.isFunction(_options.html)) {
						if ($.type(_options.html) == 'string') {
							var htmlStr = _options.html;
						} else {
							var htmlStr = _options.html[0].outerHTML;
						}
						if (htmlStr.search(/<form/) != -1 || elemObj.find('.' + elemClass + '_content').outerHeight() < 200) {
							elemObj.find('.' + elemClass + '_content').removeClass(elemClass + '_contentOverflow');
						}
					}
					

					if (_options.onLoad && $.isFunction(_options.onLoad)) {
						_options.onLoad();
					}

					elemObj.on('destroyed', function() {
						if (_options.onClose && $.isFunction(_options.onClose)) {
							_options.onClose();
						}
					});

					getView();
					getPlacement();

					if (_options.onDocumentClickClose) {
						$('html').on('click.' + elemClass + 'Event', function(event) {
							$(this).addClass(elemClass + 'Open');
							if (elemObj.is(':hidden')) {
								popModalClose();
							}
							var target = $(event.target);
							if (!target.parents().andSelf().is('.' + elemClass) && !target.parents().andSelf().is(elem)) {
								var zIndex = parseInt(target.parents().filter(function() {
									return $(this).css('zIndex') !== 'auto';
								}).first().css('zIndex'));
								if (isNaN(zIndex)) {
									zIndex = 0;
								}
								var target_zIndex = target.css('zIndex');
								if (target_zIndex == 'auto') {
									target_zIndex = 0;
								}
								if (zIndex < target_zIndex) {
									zIndex = target_zIndex;
								}
								if (zIndex <= elemObj.css('zIndex')) {
									popModalClose();
								}
							}
						});
					}
					
					$(window).resize(function() {
						getPlacement();
					});
					
					elemObj.find('.close').bind('click', function() {
						popModalClose();
					});
					
					elemObj.find('[data-popModalBut="close"]').bind('click', function() {
						popModalClose();
					});

					elemObj.find('[data-popModalBut="ok"]').bind('click', function(event) {
						var ok;
						if (_options.onOkBut && $.isFunction(_options.onOkBut)) {
							ok = _options.onOkBut(event);
						}
						if (ok !== false) {
							popModalClose();
						}
					});

					elemObj.find('[data-popModalBut="cancel"]').bind('click', function() {
						if (_options.onCancelBut && $.isFunction(_options.onCancelBut)) {
							_options.onCancelBut();
						}
						popModalClose();
					});

					$('html').on('keydown.' + elemClass + 'Event', function(event) {
						if (event.keyCode == 27) {
							popModalClose();
						}
					});

				}
				
			},
			hide : function() {
				popModalClose();
			}
		};
		
		function getView() {
			expandView = true;
			if (elem.parent().css('position') != 'absolute' || elem.parent().css('position') != 'fixed') {
				if (elemObj.find('.' + elemClass + '_content').width() < 270 && elemObj.find('.' + elemClass + '_content').height() < 60) {
					expandView = false;
				}
			}
		}
		
		function getPlacement() {
			var offset = 10,
			eLeft = elem.position().left,
			eTop = elem.position().top,
			eMLeft = parseInt(elem.css('marginLeft')),
			ePLeft = parseInt(elem.css('paddingLeft')),
			eMTop = parseInt(elem.css('marginTop')),
			eHeight = elem.outerHeight(),
			eWidth = elem.outerWidth(),
			eObjMaxWidth = parseInt(elemObj.css('maxWidth')),
			eObjMinWidth = parseInt(elemObj.css('minWidth')),
			eObjWidth,
			eObjHeight = elemObj.outerHeight();
			
			if (expandView) {
				if (isNaN(eObjMaxWidth)) {
					eObjMaxWidth = 300;
				}
				eObjWidth = eObjMaxWidth;
			} else {
				if (isNaN(eObjMinWidth)) {
					eObjMinWidth = 180;
				}
				eObjWidth = eObjMinWidth;
			}
			elemObj.css({width: eObjWidth + 'px'});
			
			var placement,
			eOffsetLeft = elem.offset().left,
			eOffsetRight = $(window).width() - elem.offset().left - eWidth,
			eOffsetTop = elem.offset().top,
			deltaL = eOffsetLeft - offset - eObjWidth,
			deltaBL = eWidth + eOffsetLeft - eObjWidth,
			deltaR = eOffsetRight - offset - eObjWidth,
			deltaBR = eWidth + eOffsetRight - eObjWidth,
			deltaCL = eWidth / 2 + eOffsetLeft - eObjWidth / 2,
			deltaCR = eWidth / 2 + eOffsetRight - eObjWidth / 2,
			deltaC = Math.min(deltaCR, deltaCL),
			deltaCT = eOffsetTop - eObjHeight / 2;

			function optimalPosition(current) {
				var optimal;
				var maxDelta = Math.max(deltaBL, deltaBR, deltaC);
				if (isCurrentFits(current)) {
				  optimal = current;
				} else if (deltaBR > 0 && deltaBR == maxDelta) {
					optimal = bl;
				} else if (deltaBL > 0 && deltaBL == maxDelta) {
					optimal = br;
				} else if (deltaBC > 0 && deltaC == maxDelta) {
					optimal = bc;
				} else {
					optimal = current;
				}
				return optimal;
			}
			
			function isCurrentFits(current) {
			  return current == bl ? deltaBR > 0 
				: current == br ? deltaBL > 0 
				: deltaC > 0;
			}
			
			if ((/^bottom/).test(_options.placement)) {
				placement = optimalPosition(_options.placement);
			} else if ((/^left/).test(_options.placement)) {
				if (deltaL > 0) {
					if (_options.placement == lc && deltaCT > 0) {
						placement = lc;
					} else {
						placement = lt;
					}
				} else {
					placement = optimalPosition(bl);
				}
			} else if ((/^right/).test(_options.placement)) {
				if (deltaR > 0) {
					if (_options.placement == rc && deltaCT > 0) {
						placement = rc;
					} else {
						placement = rt;
					}
				} else {
					placement = optimalPosition(br);
				}
			}
			
			elemObj.removeAttr('class').addClass(elemClass + ' animated ' + placement);
			switch (placement){
				case (bl):
					elemObj.css({
						top: eTop + eMTop + eHeight + offset + 'px',
						left: eLeft + eMLeft + 'px'
					}).addClass(effectIn + 'Bottom');
				break;
				case (br):
					elemObj.css({
						top: eTop + eMTop + eHeight + offset + 'px',
						left: eLeft + eMLeft + eWidth - eObjWidth + 'px'
					}).addClass(effectIn + 'Bottom');
				break;
				case (bc):
					elemObj.css({
						top: eTop + eMTop + eHeight + offset + 'px',
						left: eLeft + eMLeft + (eWidth - eObjWidth) / 2 + 'px'
					}).addClass(effectIn + 'Bottom');
				break;
				case (lt):
					elemObj.css({
						top: eTop + eMTop + 'px',
						left: eLeft + eMLeft - eObjWidth - offset + 'px'
					}).addClass(effectIn + 'Left');
				break;
				case (rt):
					elemObj.css({
						top: eTop + eMTop + 'px',
						left: eLeft + eMLeft + eWidth + offset + 'px'
					}).addClass(effectIn + 'Right');
				break;
				case (lc):
					elemObj.css({
						top: eTop + eMTop + eHeight / 2 - eObjHeight / 2 + 'px',
						left: eLeft + eMLeft - eObjWidth - offset + 'px'
					}).addClass(effectIn + 'Left');
				break;
				case (rc):
					elemObj.css({
						top: eTop + eMTop + eHeight / 2 - eObjHeight / 2 + 'px',
						left: eLeft + eMLeft + eWidth + offset + 'px'
					}).addClass(effectIn + 'Right');
				break;
			}
		}
		
		function popModalClose() {
			elemObj = $('.' + elemClass);
			reverseEffect();
			getAnimTime();
			setTimeout(function () {
				$('.' + elemClass + '_source').replaceWith($('.' + elemClass + '_content').children());
				elemObj.remove();
				$('html.' + elemClass + 'Open').off('.' + elemClass + 'Event').removeClass(elemClass + 'Open');
			}, animTime);
		}
		
		function getAnimTime() {
			if (!animTime) {
				animTime = elemObj.css('animationDuration');
				if (animTime != undefined) {
					animTime = animTime.replace('s', '') * 1000;
				} else {
					animTime = 0;
				}
			}
		}
		
		function reverseEffect() {
			var animClassOld = elemObj.attr('class'),
			animClassNew = animClassOld.replace(effectIn, effectOut);
			elemObj.removeClass(animClassOld).addClass(animClassNew);
		}

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || ! method) {
			return methods.init.apply(this, arguments);
		}

	}

	$('* [data-popModalBind]').bind('click', function() {
		var elemBind = $(this).attr('data-popModalBind');
		var params = {html: $(elemBind)};
		if ($(this).attr('data-placement') != undefined) {
			params['placement'] = $(this).attr('data-placement');
		}
		if ($(this).attr('data-showCloseBut') != undefined) {
			params['showCloseBut'] = (/^true$/i).test($(this).attr('data-showCloseBut'));
		}
		if ($(this).attr('data-overflowContent') != undefined) {
			params['overflowContent'] = (/^true$/i).test($(this).attr('data-overflowContent'));
		}
		if ($(this).attr('data-onDocumentClickClose') != undefined) {
			params['onDocumentClickClose'] = (/^true$/i).test($(this).attr('data-onDocumentClickClose'));
		}
		$(this).popModal(params);
	});
	
  $.event.special.destroyed = {
    remove: function(o) {
      if (o.handler) {
        o.handler();
      }
    }
  }
})(jQuery);



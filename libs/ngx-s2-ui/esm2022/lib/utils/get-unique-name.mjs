export const getUniqueName = (name, usedNames) => {
    if (usedNames.some((usedName) => usedName === name)) {
        const suffixNumber = usedNames.reduce((result, usedName) => {
            if (usedName !== name && usedName?.startsWith(name)) {
                const usedNameEnd = usedName.slice(name.length);
                if (/^ \(\d+\)$/.test(usedNameEnd)) {
                    const usedNameSuffixNumber = parseInt(/\d+/.exec(usedNameEnd)[0]);
                    if (usedNameSuffixNumber >= result) {
                        result = usedNameSuffixNumber + 1;
                    }
                }
            }
            return result;
        }, 1);
        return getUniqueName(`${name} (${suffixNumber})`, usedNames);
    }
    return name;
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZ2V0LXVuaXF1ZS1uYW1lLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLXVpL3NyYy9saWIvdXRpbHMvZ2V0LXVuaXF1ZS1uYW1lLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE1BQU0sQ0FBQyxNQUFNLGFBQWEsR0FBRyxDQUFDLElBQVksRUFBRSxTQUFtQixFQUFVLEVBQUU7SUFDekUsSUFBSSxTQUFTLENBQUMsSUFBSSxDQUFDLENBQUMsUUFBUSxFQUFFLEVBQUUsQ0FBQyxRQUFRLEtBQUssSUFBSSxDQUFDLEVBQUU7UUFDbkQsTUFBTSxZQUFZLEdBQUcsU0FBUyxDQUFDLE1BQU0sQ0FBQyxDQUFDLE1BQU0sRUFBRSxRQUFRLEVBQUUsRUFBRTtZQUN6RCxJQUFJLFFBQVEsS0FBSyxJQUFJLElBQUksUUFBUSxFQUFFLFVBQVUsQ0FBQyxJQUFJLENBQUMsRUFBRTtnQkFDbkQsTUFBTSxXQUFXLEdBQUcsUUFBUSxDQUFDLEtBQUssQ0FBQyxJQUFJLENBQUMsTUFBTSxDQUFDLENBQUM7Z0JBQ2hELElBQUksWUFBWSxDQUFDLElBQUksQ0FBQyxXQUFXLENBQUMsRUFBRTtvQkFDbEMsTUFBTSxvQkFBb0IsR0FBRyxRQUFRLENBQUMsS0FBSyxDQUFDLElBQUksQ0FBQyxXQUFXLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO29CQUNsRSxJQUFJLG9CQUFvQixJQUFJLE1BQU0sRUFBRTt3QkFDbEMsTUFBTSxHQUFHLG9CQUFvQixHQUFHLENBQUMsQ0FBQztxQkFDbkM7aUJBQ0Y7YUFDRjtZQUNELE9BQU8sTUFBTSxDQUFDO1FBQ2hCLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQztRQUNOLE9BQU8sYUFBYSxDQUFDLEdBQUcsSUFBSSxLQUFLLFlBQVksR0FBRyxFQUFFLFNBQVMsQ0FBQyxDQUFDO0tBQzlEO0lBQ0QsT0FBTyxJQUFJLENBQUM7QUFDZCxDQUFDLENBQUMiLCJzb3VyY2VzQ29udGVudCI6WyJleHBvcnQgY29uc3QgZ2V0VW5pcXVlTmFtZSA9IChuYW1lOiBzdHJpbmcsIHVzZWROYW1lczogc3RyaW5nW10pOiBzdHJpbmcgPT4ge1xyXG4gIGlmICh1c2VkTmFtZXMuc29tZSgodXNlZE5hbWUpID0+IHVzZWROYW1lID09PSBuYW1lKSkge1xyXG4gICAgY29uc3Qgc3VmZml4TnVtYmVyID0gdXNlZE5hbWVzLnJlZHVjZSgocmVzdWx0LCB1c2VkTmFtZSkgPT4ge1xyXG4gICAgICBpZiAodXNlZE5hbWUgIT09IG5hbWUgJiYgdXNlZE5hbWU/LnN0YXJ0c1dpdGgobmFtZSkpIHtcclxuICAgICAgICBjb25zdCB1c2VkTmFtZUVuZCA9IHVzZWROYW1lLnNsaWNlKG5hbWUubGVuZ3RoKTtcclxuICAgICAgICBpZiAoL14gXFwoXFxkK1xcKSQvLnRlc3QodXNlZE5hbWVFbmQpKSB7XHJcbiAgICAgICAgICBjb25zdCB1c2VkTmFtZVN1ZmZpeE51bWJlciA9IHBhcnNlSW50KC9cXGQrLy5leGVjKHVzZWROYW1lRW5kKVswXSk7XHJcbiAgICAgICAgICBpZiAodXNlZE5hbWVTdWZmaXhOdW1iZXIgPj0gcmVzdWx0KSB7XHJcbiAgICAgICAgICAgIHJlc3VsdCA9IHVzZWROYW1lU3VmZml4TnVtYmVyICsgMTtcclxuICAgICAgICAgIH1cclxuICAgICAgICB9XHJcbiAgICAgIH1cclxuICAgICAgcmV0dXJuIHJlc3VsdDtcclxuICAgIH0sIDEpO1xyXG4gICAgcmV0dXJuIGdldFVuaXF1ZU5hbWUoYCR7bmFtZX0gKCR7c3VmZml4TnVtYmVyfSlgLCB1c2VkTmFtZXMpO1xyXG4gIH1cclxuICByZXR1cm4gbmFtZTtcclxufTtcclxuIl19